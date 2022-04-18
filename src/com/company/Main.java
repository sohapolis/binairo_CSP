package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
;

//hi

public class Main {

    public static void main(String[] args) {

        File input = new File("C:\\Users\\Soha\\IdeaProjects\\CSP\\src\\com\\company\\inputs\\inputTEST.txt");
        ArrayList<ArrayList<String>> board = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<String>>> domain = new ArrayList<>();
        try {
            Scanner reader = new Scanner(input);
            int n = reader.nextInt();
            for (int i = 0; i < n; i++) {
                board.add(new ArrayList<>());
                domain.add(new ArrayList<>());
                for (int j = 0; j < n; j++) {
                    board.get(i).add("E"); //empty
                    domain.get(i).add(new ArrayList<>(Arrays.asList(
                            "w",
                            "b"
                    )));
                }
            }

            int m = reader.nextInt();

            int[][] listP=new int[2][m];

            for (int i = 0; i < m; i++) {
                int y = reader.nextInt();
                int x = reader.nextInt();
                int z = reader.nextInt();
                listP[0][i]=y;
                listP[1][i]=x;
                String a = switch (z) {
                    case 0 -> "W"; //white
                    case 1 -> "B"; //black
                    default -> null;
                };
                board.get(y).set(x, a);
                domain.get(y).set(x, new ArrayList<>(List.of(
                        "n"
                )));

            } //Board and Domain initialized
            Binairo binairo = new Binairo(board, domain, n,listP,m);
            binairo.start();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
