/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
/**
 *
 * @author josia
 * @author david
 */
public class Assignment2 {

static Dictionary my_dictionary = new Dictionary();

    public static void loadWords() {
        try {
            //Passing from file to an array of strings
            File wordFile = new File("wordsList.txt");
            Scanner fileScanner = new Scanner(wordFile);
            String[] words = new String[Integer.parseInt(fileScanner.nextLine())];

            for (int i = 0; i < words.length; i++) {
                if (fileScanner.hasNextLine()) {
                    words[i] = fileScanner.nextLine();
                }
            }
            insertIntoDictionary(words);
            fileScanner.close();
        } catch (Exception e) { //Throw exception if file is not found
            System.out.println("File not found");
        }
    }

    public static void insertIntoDictionary(String[] words) {
        if (words.length != 0) {
            int mid = (words.length - 1) / 2;
            my_dictionary.add(words[mid], "Undefined word");
            String[] leftArray = Arrays.copyOfRange(words, 0, mid);
            String[] rightArray = Arrays.copyOfRange(words, mid + 1, words.length);
            //Recursively calls itself to add to dictionary
            insertIntoDictionary(leftArray);
            insertIntoDictionary(rightArray);
        }
    }

    public static void menu() {
        int choice = 0;
        do {
            System.out.println(
                    "Select an item from the menu\n" +
                            "1: Add new word\n" +
                            "2: Delete word\n" +
                            "3: Get meaning\n" +
                            "4: Dictionary list\n" +
                            "5: Spell check a text file\n" +
                            "6: Exit\n");
            Scanner userInput = new Scanner(System.in);
            System.out.print("Choice : ");
            choice = userInput.nextInt();
            String word, meaning;
            boolean result;
            switch (choice) {
                case 1:
                    System.out.print("Enter new word : ");
                    word = userInput.next().toLowerCase();
                    System.out.print("Enter new word meaning : ");
                    meaning = userInput.next();
                    result = my_dictionary.add(word, meaning);
                    if (result) System.out.println("Word added to dictionary!");
                    else System.out.println("Word is already in dictionary!");
                    break;
                case 2:
                    System.out.print("Enter word to be deleted : ");
                    word = userInput.next().toLowerCase();
                    result = my_dictionary.delete(word);
                    if (result) System.out.println("Word deleted from dictionary!");
                    else System.out.println("Word not found in dictionary!");
                    break;
                case 3:
                    System.out.print("Enter word to get meaning for : ");
                    word = userInput.next().toLowerCase();
                    String output = my_dictionary.getMeaning(word);
                    System.out.println(output);
                    break;
                case 4:
                    System.out.println(my_dictionary.printWordList());
                    break;
                case 5:
                    String filename;
                    System.out.print("Enter the file name : ");
                    filename = userInput.next();
                    try {
                        File userFile = new File(filename);
                        Scanner fileScanner = new Scanner(userFile);
                        while (fileScanner.hasNextLine()) {
                            String[] line = fileScanner.nextLine().split(" ");
                            for (String text: line) {
                                if (!text.equals(".") && !text.equals(",")) {
                                    if (!my_dictionary.exists(text))
                                        System.out.println(text);
                                }
                            }
                        }

                    } catch (FileNotFoundException e) {
                        System.out.println("File not found");
                    }
                    break;
                case 6:
                    System.out.println("Exiting Program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Exiting program.");
            }
        } while (choice < 7 && choice > 0);

    }

    public static void main(String[] args) {
        loadWords();
        menu();
    }
}
