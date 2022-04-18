package com.company;
import java.util.ArrayList;

public class Binairo {
    private final ArrayList<ArrayList<String>> board;
    private final ArrayList<ArrayList<ArrayList<String>>> domain;
    private final int n;

    public Binairo(ArrayList<ArrayList<String>> board, ArrayList<ArrayList<ArrayList<String>>> domain, int n, int[][] listP,int m) {
        this.n = n;
        State state = new State(board, domain);

        for (int i = 0; i <m ; i++) {
            forwardChecking(state,listP[0][i],listP[1][i]);
        }
        this.board = board;
        this.domain = state.getDomain();
    }

    public void start() {
        long tStart = System.nanoTime();
        State state = new State(board, domain);

        drawLine();
        System.out.println("Initial Board: \n");
        state.printBoard();
        drawLine();
        state.printDomain();

        //backtrack(state);
        long tEnd = System.nanoTime();
        System.out.println("Total time: " + (tEnd - tStart)/1000000000.000000000);
    }

    private boolean forwardChecking(State state,int x,int y){
        String cur=state.getBoard().get(x).get(y);

        int numberOfWhites = 0;
        int numberOfBlacks = 0;
        for (int i = 0; i <n; i++) {
            String a=state.getBoard().get(x).get(i);
            switch (a) {
                case "w", "W" -> numberOfWhites++;
                case "b", "B" -> numberOfBlacks++;
            }
        }
        if(numberOfBlacks==n/2){
            for (int i = 0; i <n; i++) {
                state.getDomain().get(x).get(i).remove("b");
                if (state.getDomain().get(x).get(i).size()==0){
                    return false;
                }
            }
        }
        if(numberOfWhites==n/2){
            for (int i = 0; i <n; i++) {
                state.getDomain().get(x).get(i).remove("w");
                if (state.getDomain().get(x).get(i).size()==0){
                    return false;
                }
            }
        }

        if(y>0){
            if (state.getBoard().get(x).get(y-1).equals(cur)){
                if (y>1) {
                    state.getDomain().get(x).get(y - 2).remove(cur.toLowerCase());
                    if (state.getDomain().get(x).get(y-2).size()==0){return false;}
                }
                if(y<n-1){
                    state.getDomain().get(x).get(y +1).remove(cur.toLowerCase());
                    if (state.getDomain().get(x).get(y+1).size()==0){return false;}
                }
            }
        }
        if(y<n-1 ){
            if (state.getBoard().get(x).get(y+1).equals(cur)){
                if(y<n-2) {
                    state.getDomain().get(x).get(y + 2).remove(cur.toLowerCase());
                    if (state.getDomain().get(x).get(y + 2).size() == 0) {
                        return false;
                    }
                }
                if(y>0){
                    state.getDomain().get(x).get(y-1).remove(cur.toLowerCase());
                    if (state.getDomain().get(x).get(y-1).size() == 0) {
                        return false;
                    }
                }
            }
        }

        //column

        numberOfWhites = 0;
        numberOfBlacks = 0;
        for (int i = 0; i <n; i++) {
            String a=state.getBoard().get(i).get(y);
            switch (a) {
                case "w", "W" -> numberOfWhites++;
                case "b", "B" -> numberOfBlacks++;
            }
        }
        if(numberOfBlacks==n/2){
            for (int i = 0; i <n; i++) {
                state.getDomain().get(i).get(y).remove("b");
                if (state.getDomain().get(i).get(y).size()==0){
                    return false;
                }
            }
        }
        if(numberOfWhites==n/2){
            for (int i = 0; i <n; i++) {
                state.getDomain().get(i).get(y).remove("w");
                if (state.getDomain().get(i).get(y).size()==0){
                    return false;
                }
            }
        }

        if(x>0){
            if (state.getBoard().get(x-1).get(y).equals(cur)){
                if (x>1) {
                    state.getDomain().get(x-2).get(y).remove(cur.toLowerCase());
                    if (state.getDomain().get(x-2).get(y).size()==0){return false;}
                }
                if(x<n-1){
                    state.getDomain().get(x+1).get(y).remove(cur.toLowerCase());
                    if (state.getDomain().get(x+1).get(y).size()==0){return false;}
                }
            }
        }
        if(x<n-1 ){
            if (state.getBoard().get(x+1).get(y).equals(cur)){
                if(x<n-2) {
                    state.getDomain().get(x+2).get(y).remove(cur.toLowerCase());
                    if (state.getDomain().get(x+2).get(y).size() == 0) {
                        return false;
                    }
                }
                if(x>0){
                    state.getDomain().get(x-1).get(y).remove(cur.toLowerCase());
                    if (state.getDomain().get(x-1).get(y).size() == 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean checkNumberOfCircles(State state) {
        ArrayList<ArrayList<String>> cBoard = state.getBoard();
        //row
        for (int i = 0; i < n; i++) {
            int numberOfWhites = 0;
            int numberOfBlacks = 0;
            for (int j = 0; j < n; j++) {
                String a = cBoard.get(i).get(j);
                switch (a) {
                    case "w", "W" -> numberOfWhites++;
                    case "b", "B" -> numberOfBlacks++;
                }
            }
            if (numberOfBlacks > n/2 || numberOfWhites > n/2) {
                return false;
            }
        }
        //column
        for (int i = 0; i < n; i++) {
            int numberOfWhites = 0;
            int numberOfBlacks = 0;
            for (int j = 0; j < n; j++) {
                String a = cBoard.get(j).get(i);
                switch (a) {
                    case "w", "W" -> numberOfWhites++;
                    case "b", "B" -> numberOfBlacks++;
                }
            }
            if (numberOfBlacks > n/2 || numberOfWhites > n/2) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAdjacency(State state) {
        ArrayList<ArrayList<String>> cBoard = state.getBoard();

        //Horizontal
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n-2; j++) {
                ArrayList<String> row = cBoard.get(i);
                String c1 = row.get(j).toUpperCase();
                String c2 = row.get(j+1).toUpperCase();
                String c3 = row.get(j+2).toUpperCase();
                if (c1.equals(c2) && c2.equals(c3) && !c1.equals("E")) {
                    return false;
                }
            }
        }
        //column
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n-2; i++) {
                String c1 = cBoard.get(i).get(j).toUpperCase();
                String c2 = cBoard.get(i+1).get(j).toUpperCase();
                String c3 = cBoard.get(i+2).get(j).toUpperCase();
                if (c1.equals(c2) && c2.equals(c3) && !c1.equals("E")) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkIfUnique (State state) {
        ArrayList<ArrayList<String>> cBoard = state.getBoard();

        //check if two rows are duplicated
        for (int i = 0; i < n-1; i++) {
            for (int j = i+1; j < n; j++) {
                int count = 0;
                for (int k = 0; k < n; k++) {
                    String a = cBoard.get(i).get(k);
                    if (a.equals(cBoard.get(j).get(k)) && !a.equals("E")) {
                        count++;
                    }
                }
                if (count == n) {
                    return false;
                }
            }
        }

        //check if two columns are duplicated

        for (int j = 0; j < n-1; j++) {
            for (int k = j+1; k < n; k++) {
                int count = 0;
                for (int i = 0; i < n; i++) {
                    if (cBoard.get(i).get(j).equals(cBoard.get(i).get(k))) {
                        count++;
                    }
                }
                if (count == n) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean allAssigned(State state) {
        ArrayList<ArrayList<String>> cBoard = state.getBoard();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String s = cBoard.get(i).get(j);
                if (s.equals("E"))
                    return false;
            }
        }
        return true;
    }


    private boolean isFinished(State state) {
        return allAssigned(state) && checkAdjacency(state) && checkNumberOfCircles(state) && checkIfUnique(state);
    }

    private boolean isConsistent(State state) {
        return checkNumberOfCircles(state) && checkAdjacency(state) && checkIfUnique(state);
    }

    private void drawLine() {
        for (int i = 0; i < n*2; i++) {
            System.out.print("\u23E4\u23E4");
        }
        System.out.println();
    }
}

