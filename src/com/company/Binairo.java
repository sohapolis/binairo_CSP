package com.company;
import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Binairo {
    private final ArrayList<ArrayList<String>> board;
    private final ArrayList<ArrayList<ArrayList<String>>> domain;
    private final int n;
    private State finalState;

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
        //state.printDomain();
        //System.out.println("lcv color : "+LCV(state,0,2));
        //System.out.println(MRV(state)[0]+","+MRV(state)[1]);
        if (isConsistent(state)) {
            //backtrack(state);
            backtrackAC3(state,0);

            if (finalState==null) {
                System.out.println("failed");
                drawLine();
            }
            else
            {
                System.out.println("Result board:");
                finalState.printBoard();
                drawLine();
                //System.out.println("////////////////");
                //finalState.printDomain();
            }

        }
        else {
            System.out.println("failed");
            drawLine();;
        }
        long tEnd = System.nanoTime();
        System.out.println("Total time: " + (tEnd - tStart)/1000000000.000000000);
    }

    private int forwardChecking(State state,int x,int y){
        int count=0;
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
                if(state.getDomain().get(x).get(i).remove("b")){
                    count++;
                }
                if (state.getDomain().get(x).get(i).size()==0){
                    return -1;
                }
            }
        }
        if(numberOfWhites==n/2){
            for (int i = 0; i <n; i++) {
                if(state.getDomain().get(x).get(i).remove("w")){
                    count++;
                }
                if (state.getDomain().get(x).get(i).size()==0){
                    return -1;
                }
            }
        }

        if(y>0){
            if (state.getBoard().get(x).get(y-1).equals(cur)){
                if (y>1) {
                    if(state.getDomain().get(x).get(y - 2).remove(cur.toLowerCase())){
                      count++;
                    }
                    if (state.getDomain().get(x).get(y-2).size()==0){return -1;}
                }
                if(y<n-1){
                    if(state.getDomain().get(x).get(y +1).remove(cur.toLowerCase())){
                        count++;
                    }
                    if (state.getDomain().get(x).get(y+1).size()==0){return -1;}
                }
            }
        }
        if(y<n-1 ){
            if (state.getBoard().get(x).get(y+1).equals(cur)){
                if(y<n-2) {
                    if (state.getDomain().get(x).get(y + 2).remove(cur.toLowerCase())){
                        count++;
                    }
                    if (state.getDomain().get(x).get(y + 2).size() == 0) {
                        return -1;
                    }
                }
                if(y>0){
                    if(state.getDomain().get(x).get(y-1).remove(cur.toLowerCase())){
                        count++;
                    }
                    if (state.getDomain().get(x).get(y-1).size() == 0) {
                        return -1;
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
                if (state.getDomain().get(i).get(y).remove("b")){
                    count++;
                }
                if (state.getDomain().get(i).get(y).size()==0){
                    return -1;
                }
            }
        }
        if(numberOfWhites==n/2){
            for (int i = 0; i <n; i++) {
                if(state.getDomain().get(i).get(y).remove("w")){
                    count++;
                }
                if (state.getDomain().get(i).get(y).size()==0){
                    return -1;
                }
            }
        }

        if(x>0){
            if (state.getBoard().get(x-1).get(y).equals(cur)){
                if (x>1) {
                    if (state.getDomain().get(x-2).get(y).remove(cur.toLowerCase())){
                        count++;
                    }
                    if (state.getDomain().get(x-2).get(y).size()==0){return -1;}
                }
                if(x<n-1){
                    if (state.getDomain().get(x+1).get(y).remove(cur.toLowerCase())){
                        count++;
                    }
                    if (state.getDomain().get(x+1).get(y).size()==0){return -1;}
                }
            }
        }
        if(x<n-1 ){
            if (state.getBoard().get(x+1).get(y).equals(cur)){
                if(x<n-2) {
                    if (state.getDomain().get(x+2).get(y).remove(cur.toLowerCase())){
                        count++;
                    }
                    if (state.getDomain().get(x+2).get(y).size() == 0) {
                        return -1;
                    }
                }
                if(x>0){
                    if(state.getDomain().get(x-1).get(y).remove(cur.toLowerCase())){
                        count++;
                    }
                    if (state.getDomain().get(x-1).get(y).size() == 0) {
                        return -1;
                    }
                }
            }
        }

        return count;
    }

    private int[] MRV(State state){
        int[] point=new int[2];
        for (int i = 0; i <n ; i++) {
            for (int j = 0; j <n ; j++) {
                if (state.getDomain().get(i).get(j).size() == 1 ) {
                    if (!state.getDomain().get(i).get(j).get(0).equals("n")) return new int[]{i, j};
                    continue;
                }
                point= new int[]{i, j};
            }
        }
        return point;
    }

    private String LCV(State state,int x,int y){
        int min=Integer.MAX_VALUE;
        String s=null;
        for (int i = 0; i <state.getDomain().get(x).get(y).size(); i++) {
            String cur = state.getDomain().get(x).get(y).get(i);
            State temp= state.copy();

            /*
            System.out.println("////////////////////");
            temp.printDomain();
            System.out.println("////////////////////");
            temp.printBoard();
             */

            temp.getBoard().get(x).set(y,cur.toUpperCase());
            temp.getDomain().get(x).set(y, new ArrayList<>(List.of("n")));
            int compering=forwardChecking(temp,x,y);
            //System.out.println("point "+x+","+y+" cur: "+cur+ " compering:"+compering);
            if (compering < min) {
                min = compering;
                s=cur;
            }
        }
        return s;

    }

    private boolean AC3(State state){
        //ArrayList<ArrayList<ArrayList<String>>> list=state.getDomain();
        for (int i = 0; i <n ; i++) {
            for (int j = 0; j <n ; j++) {
                if (!state.getDomain().get(i).get(j).get(0).equals("n")){
                    ArrayList<String> domain=new ArrayList<>();
                    for (String d:state.getDomain().get(i).get(j)) {
                        domain.add(d);
                        State temp=state.copy();
                        temp.getDomain().get(i).set(j,new ArrayList<>(List.of("n")));
                        temp.getBoard().get(i).set(j,d.toUpperCase());
                        if (forwardChecking(temp,i,j)==-1 || !checkIfUnique(temp)){
                            domain.remove(d);
                        }
                    }
                    if (domain.size()==0){
                        return false;
                    }
                    state.getDomain().get(i).set(j,domain);
                }
            }

        }
        return true;
    }

    private boolean backtrackAC3(State state,int count){
        if (isFinished(state)){
            this.finalState=state;
            return true;
        }
        int[] point=MRV(state);
        String color=LCV(state,point[0],point[1]);
        ArrayList<String> priority=new ArrayList();
        for (String s:state.getDomain().get(point[0]).get(point[1])) {
            if (s.equals(color)){
                priority.add(0,s);
            }
            else {
                priority.add(s);
            }
        }
        /*
        System.out.println(point[0]+","+point[1]);
        System.out.println(color);
        System.out.println(priority);

         */
        for (String s:priority) {
            count++;
            State temp=state.copy();
            temp.getBoard().get(point[0]).set(point[1],s);
            temp.getDomain().get(point[0]).set(point[1],new ArrayList<>(List.of("n")));
            if (isConsistent(temp)) {
                if (forwardChecking(temp,point[0],point[1])!=-1) {
                    if (AC3(temp)) {
                        if (backtrackAC3(temp,count)) {
                            // this.finalState = temp;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean backtrack(State state){
        if (isFinished(state)){
            this.finalState=state;
            return true;
        }
        int[] point=MRV(state);
        String color=LCV(state,point[0],point[1]);
        ArrayList<String> priority=new ArrayList();
        for (String s:state.getDomain().get(point[0]).get(point[1])) {
            if (s.equals(color)){
                priority.add(0,s);
            }
            else {
                priority.add(s);
            }
        }
        /*
        System.out.println(point[0]+","+point[1]);
        System.out.println(color);
        System.out.println(priority);

         */
        for (String s:priority) {
            State temp=state.copy();
            temp.getBoard().get(point[0]).set(point[1],s);
            temp.getDomain().get(point[0]).set(point[1],new ArrayList<>(List.of("n")));
            if (isConsistent(temp)) {
                if (forwardChecking(temp,point[0],point[1])!=-1) {
                    if (backtrack(temp)) {
                       // this.finalState = temp;
                        return true;
                    }
                }
            }
        }
        return false;
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
                    if (cBoard.get(i).get(j).equals(cBoard.get(i).get(k))&& !cBoard.get(i).get(j).equals("E")) {
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

