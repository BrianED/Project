import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class is15123529
{
    /**
     * TODO
     * @param args
     */
    public static void main(String[] args)
    {
        int ed;
        int[] params = new int[8];                              // Array to hold user input parameters
        String[] dialogues = {
                "Generations: ",                                // 0 g
                "Population: ",                                 // 1 p
                "Students: ",                                   // 2 s
                "Total modules: ",                              // 3 e
                "Course modules: ",                             // 4 c
                "Exam sessions/day: ",                          // 5 d
                "Crossover probability: ",                      // 6 cr
                "Mutation probability: "                        // 7 mu
        };
        String[] errors = {
                "Generations must be positive.",
                "Population must be positive.",
                "Students must be positive.",
                "Total modules must be greater than course modules and both must be greater than 0",
                "Total modules must be greater than course modules and both must be greater than 0",
                "Exam sessions/day must be positive.",
                "Crossover probability must be positive.",
                "Mutation probability must be positive."
        };
        params = userInput(dialogues, errors, params);          // Collect user input
        ed = (int) Math.ceil((double)params[3] / params[5]);
        int[][] studentSchedule = new int[params[2]][params[4]];// Student schedule (num of students x course modules)
        ArrayList<Integer> uniqueRow = generateRow(params[3]);  // Generate unique row from total number of modules
        studentSchedule = addRows(studentSchedule, uniqueRow);  // Add shuffled unique rows to each row in the 2D array
        printStudentSchedule(studentSchedule);                  // Print student schedule to console
    }

    /**
     * TODO
     * @param dialogues
     * @param errors
     * @param params
     * @return
     */
    private static int[] userInput(String[] dialogues, String[] errors, int[] params)
    {
        int re;
        String userInput, tempCourse, tempMu;
        String pattern = "[0-9]+"; // Numbers 0 to 9 (1 or more)
        boolean valid;

        for (int i = 0; i < params.length; i++) {
            valid = false;
            while (!valid) {
                userInput = JOptionPane.showInputDialog(null, dialogues[i]);
                if (userInput == null || userInput.length() == 0) System.exit(1);
                if (!userInput.matches(pattern))
                    JOptionPane.showMessageDialog(null, errors[i], "Error", JOptionPane.ERROR_MESSAGE);
                else { // Check specific cases
                    if (i == 3) { // Check total modules >= course modules
                        while (true) {
                            tempCourse = JOptionPane.showInputDialog(null, "Course Modules: ");
                            if (tempCourse == null || tempCourse.length() == 0) System.exit(1);
                            if (Integer.parseInt(tempCourse) <= Integer.parseInt(userInput) &&
                                    Integer.parseInt(tempCourse) > 0) {
                                params[i] = Integer.parseInt(userInput); // Add total module num to array
                                params[++i] = Integer.parseInt(tempCourse); // Add course module num to array
                                break; // Exit loop
                            }
                            JOptionPane.showMessageDialog(null, errors[i], "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        valid = true; // Break out of while loop
                    } else if (i == 6) {
                        while (true) {
                            tempMu = JOptionPane.showInputDialog(null, "Mutation probability: ");
                            if (tempMu == null || tempMu.length() == 0) System.exit(1);
                            re = (100 - (Integer.parseInt(userInput) + (Integer.parseInt(tempMu)))); // Re = 100 - (Cr + Mu)
                            if ((Integer.parseInt(userInput) < 100) && (Integer.parseInt(tempMu) < 100) &&
                                    ((re + Integer.parseInt(userInput) + Integer.parseInt(tempMu)) == 100 ) &&
                                    (Integer.parseInt(tempMu) > 0)) {
                                params[i] = Integer.parseInt(userInput); // Add Crossover probability num to array
                                params[++i] = Integer.parseInt(tempMu); // Add Mutation probability num to array
                                break; // Exit inner loop
                            }
                            if ((Integer.parseInt(tempMu) < 0)) // Print error message for Mutation probability
                                JOptionPane.showMessageDialog(null, errors[7], "Error", JOptionPane.ERROR_MESSAGE);
                            else
                                JOptionPane.showMessageDialog(null, errors[i], "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        valid = true; // Break out of while loop
                    } else {
                        params[i] = Integer.parseInt(userInput);
                        valid = true; // Break out of while loop
                    }
                }
            }
        }

        return params;
    }

    /**
     * TODO
     * @param length
     * @return
     */
    private static ArrayList<Integer> generateRow(int length)
    {
        ArrayList<Integer> uniqueRow = new ArrayList<>();

        for (int i = 1; i <= length; i++) {
            uniqueRow.add(i);
        }
        Collections.shuffle(uniqueRow);

        return uniqueRow;
    }

    /**
     * TODO
     * @param studentSchedule
     * @param uniqueRow
     * @return
     */
    private static int[][] addRows(int[][] studentSchedule, ArrayList<Integer> uniqueRow)
    {
        // Add unique rows to 2d array
        for (int row = 0, k = 0; row < studentSchedule.length; row++, k++) {
            for (int col = 0; col < studentSchedule[row].length; col++) {
                studentSchedule[row][col] = uniqueRow.get(col);
            }
            Collections.shuffle(uniqueRow);
        }

        return studentSchedule;
    }

    /**
     * TODO
     * @param studentSchedule
     */
    private static void printStudentSchedule(int[][] studentSchedule)
    {
        for(int i = 0; i < studentSchedule.length; i++) {
            System.out.print("Student " + (i+1) + ": ");
            for(int j = 0; j < studentSchedule[i].length; j++) {
                System.out.print(studentSchedule[i][j] + " ");
            }
            System.out.println();
        }
    }
}
// System.out.println("Re: " + re);
// System.out.println("Sum of Re Cr Mu: " + (re + Integer.parseInt(userInput) + tempMu));