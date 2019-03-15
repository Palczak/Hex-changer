import CustomExceptions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FileManager {
    private HexIO hexIO;
    private File instructionDir;
    private String identifyName;
    private String findName;
    private String replaceName;
    private String fillName;
    private String beforeName;
    private String insideName;
    private String afterName;

    FileManager() {
        this.hexIO = new HexIO();
        this.instructionDir = null;
        this.identifyName = "ident";
        this.findName = "find";
        this.replaceName = "replace";
        this.fillName = "fill";
        this.beforeName = "before";
        this.insideName = "inside";
        this.afterName = "after";
    }

    void setInstructionDir(File instructionDir) {
        this.instructionDir = instructionDir;
    }

    public File getInstructionDir() {
        return instructionDir;
    }


    void setInstructionDir(String instructionDir) {
        File file = new File(instructionDir);
        setInstructionDir(file);
    }

    void saveResult(String path, Hex result) throws IOException {
        hexIO.writeHex(path, result);
    }

    Hex getCorruptedHex(String path) throws IOException {
        return hexIO.readHex(path);
    }

    InstructionSet readInstructions() throws IOException, InstructionNotSetException {
        if (!isInstructionSet()) {
            throw new InstructionNotSetException();
        }
        InstructionSet resultSet = new InstructionSet();
        resultSet.setIdentify(readIdentify());
        resultSet.setFindList(readFind());
        resultSet.setReplaceList(readReplace());
        resultSet.setFillList(readFill());


        return resultSet;
    }

    private Identify readIdentify() throws IOException {
        File identifyFile = null;
        File[] rawFilesArray = instructionDir.listFiles();

        Pattern patternIdentify = Pattern.compile(identifyName + ".*");
        Matcher matcherIdentify;

        if (rawFilesArray == null) {
            throw new IOException("Nie znaleziono plików w katalogu z instrukcjami.");
        }

        int counter = 0;
        for (File file : rawFilesArray) {
            String name = file.getName();
            matcherIdentify = patternIdentify.matcher(name);
            if (matcherIdentify.matches()) {
                if (identifyFile != null) {
                    throw new IdentifyFileException("Jest więcej niż 1 plik identyfikacyjny " + identifyName + ".");
                }
                identifyFile = rawFilesArray[counter];
            }
            counter++;
        }

        if (identifyFile == null) {
            throw new IdentifyFileException("Nie znaleziono pliku identyfikacyjnego " + identifyName + ".");
        }

        Hex identifyHex = hexIO.readHex(identifyFile.getPath());
        return new Identify(identifyHex);
    }

    private ArrayList<Find> readFind() throws IOException {
        Pattern patternFind = Pattern.compile(findName + "\\d{3}.*");
        Matcher matcherFind;
        ArrayList<Find> resultFindList = new ArrayList<>();

        try {
            File findDir = new File(instructionDir + File.separator + findName);
            File[] rawFilesArray = findDir.listFiles();
            for (File file : rawFilesArray) {
                String name = file.getName();
                matcherFind = patternFind.matcher(name);
                if (matcherFind.matches()) {
                    int index = Integer.parseInt(name.substring(findName.length(), findName.length() + 3));
                    hexIO.readHex(file);
                    resultFindList.add(new Find(hexIO.readHex(file), index));
                }
            }
        } catch (NullPointerException e) {
            throw new FindFileException("Nie znaleziono ani jednego pliku szukającego " + findName + ".");
        }

        if (resultFindList.isEmpty()) {
            throw new FindFileException("Nie wczytano ani jednego pliku szukającego " + findName + ".");
        }
        return resultFindList;
    }

    private ArrayList<Replace> readReplace() throws IOException {
        Pattern patternReplace = Pattern.compile(replaceName + "\\d{3}.*");
        Matcher matcherReplace;
        ArrayList<Replace> resultReplaceList = new ArrayList<>();
        try {
            File replaceDir = instructionDir;
            File[] rawFilesArray = replaceDir.listFiles();
            for (File file : rawFilesArray) {
                String fileReplaceName = file.getName();
                matcherReplace = patternReplace.matcher(fileReplaceName);
                if (matcherReplace.matches()) {
                    File insideReplaceDir = new File(replaceDir + File.separator + file.getName());
                    int index = Integer.parseInt(fileReplaceName.substring(replaceName.length(), replaceName.length() + 3));
                    Hex beforeHex = null;
                    Hex insideHex = null;
                    Hex afterHex = null;

                    File[] rawInstructionFilesArray = insideReplaceDir.listFiles();
                    Pattern patternBefore = Pattern.compile(beforeName + ".*");
                    Pattern patternInside = Pattern.compile(insideName + ".*");
                    Pattern patternAfter = Pattern.compile(afterName + ".*");
                    try {
                        for (File instructionFile : rawInstructionFilesArray) {
                            Matcher matcherBefore = patternBefore.matcher(instructionFile.getName());
                            Matcher matcherInside = patternInside.matcher(instructionFile.getName());
                            Matcher matcherAfter = patternAfter.matcher(instructionFile.getName());
                            if (matcherBefore.matches()) {
                                if (beforeHex != null) {
                                    throw new ReplaceFileException("Jest wiecej niż 1 plik do zamiany przed " + beforeName + " w katalogu " + instructionFile.getName() + ".");
                                }
                                beforeHex = hexIO.readHex(instructionFile);
                            } else if (matcherInside.matches()) {
                                if (insideHex != null) {
                                    throw new ReplaceFileException("Jest wiecej niż 1 plik do zamiany w środku " + insideName + " w katalogu " + instructionFile.getName() + ".");
                                }
                                insideHex = hexIO.readHex(instructionFile);

                            } else if (matcherAfter.matches()) {
                                if (afterHex != null) {
                                    throw new ReplaceFileException("Jest wiecej niż 1 plik do zamiany w po " + afterName + " w katalogu " + instructionFile.getName() + ".");
                                }
                                afterHex = hexIO.readHex(instructionFile);
                            }
                        }
                        Replace newReplace = new Replace(beforeHex, insideHex, afterHex, index);
                        resultReplaceList.add(newReplace);
                    } catch (NullPointerException e) {
                        return new ArrayList<>();
                    }
                }
            }
        } catch (NullPointerException e) {
            throw new ReplaceFileException("Nie znaleziono ani jednego pliku zamieniającego " + replaceName + ".");
        }
        return resultReplaceList;
    }

    private ArrayList<Fill> readFill() throws IOException {
        Pattern patternFill = Pattern.compile(fillName + "\\d{3}.*");
        Matcher matcherFill;
        ArrayList<Fill> resultFillList = new ArrayList<>();
        try {
            File fillDir = instructionDir;
            File[] rawFilesArray = fillDir.listFiles();
            for (File file : rawFilesArray) {
                String fileFillName = file.getName();
                matcherFill = patternFill.matcher(fileFillName);
                if (matcherFill.matches()) {
                    File insideFillDir = new File(fillDir + File.separator + file.getName());
                    int index = Integer.parseInt(fileFillName.substring(fillName.length(), fillName.length() + 3));
                    Hex beforeHex = null;
                    Hex insideHex = null;
                    Hex afterHex = null;

                    File[] rawInstructionFilesArray = insideFillDir.listFiles();
                    Pattern patternBefore = Pattern.compile(beforeName + ".*");
                    Pattern patternInside = Pattern.compile(insideName + ".*");
                    Pattern patternAfter = Pattern.compile(afterName + ".*");
                    try {
                        for (File instructionFile : rawInstructionFilesArray) {
                            Matcher matcherBefore = patternBefore.matcher(instructionFile.getName());
                            Matcher matcherInside = patternInside.matcher(instructionFile.getName());
                            Matcher matcherAfter = patternAfter.matcher(instructionFile.getName());
                            if (matcherBefore.matches()) {
                                if (beforeHex != null) {
                                    throw new FillFileException("Jest więcej niż 1 plik do uzupełnienia przed " + beforeName + " w katalogu " + instructionFile.getName() + ".");
                                }
                                beforeHex = hexIO.readHex(instructionFile);
                            } else if (matcherInside.matches()) {
                                if (insideHex != null) {
                                    throw new FillFileException("Jest więcej niż 1 plik do uzupełnienia w środku " + insideName + " w katalogu " + instructionFile.getName() + ".");
                                }
                                insideHex = hexIO.readHex(instructionFile);
                            } else if (matcherAfter.matches()) {
                                if (afterHex != null) {
                                    throw new FillFileException("Jest więcej niż 1 plik do uzupełnienia w po " + afterName + " w katalogu " + instructionFile.getName() + ".");
                                }
                                afterHex = hexIO.readHex(instructionFile);
                            }
                        }
                        Fill newFill = new Fill(beforeHex, insideHex, afterHex, index);
                        resultFillList.add(newFill);
                    } catch (NullPointerException e) {
                        return new ArrayList<>();
                    }
                }
            }
        } catch (NullPointerException e) {
            throw new FillFileException("Nie znaleziono ani jednego pliku uzupełniającego " + fillName + ".");
        }
        return resultFillList;
    }


    boolean isInstructionSet() {
        return instructionDir != null;
    }

    boolean isSet() {
        return isInstructionSet();
    }
}