import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Notebook {
    private int size;
    private ArrayList<Note> notes;

    public Notebook() throws IOException, ClassNotFoundException {
        notes=new ArrayList<>();
        FileReader reader=new FileReader("files/size.txt");
        Scanner scanner=new Scanner(reader);
        size=scanner.nextInt();
        reader.close();
        setData();
    }

    public void setData() throws IOException, ClassNotFoundException {
        ObjectInputStream input;
        for(int i=0;i<size;i++){
            input=new ObjectInputStream(new FileInputStream("files/note"+i));
            notes.add((Note) input.readObject());
            input.close();
        }
    }

    public void mainMenu() throws IOException {
        System.out.println("1. Read Notes\n2. Remove Note\n3. Add Note\n4. Exit");
        int choice=getChoice(1,4);
        switch (choice){
            case 1:
                readNotes();
                break;
            case 2:
                removeNote();
                break;
            case 3:
                addNote();
                break;
            case 4:
                System.exit(0);
        }
    }

    /**
     * Print list of notes.
     */
    private void listOfNotes(){
        int index=0;
        Iterator<Note> iterator=notes.iterator();
        while (iterator.hasNext()){
            index++;
            System.out.println(index+") "+iterator.next().getTitle());
        }
    }


    private void readNotes() throws IOException {
        listOfNotes();
        if(size==0) mainMenu();
        System.out.println(notes.get(getChoice(1,notes.size())-1).getNote());
        mainMenu();
    }

    private void removeNote() throws IOException {
        listOfNotes();
        if(size==0) mainMenu();
        Note note=notes.get(getChoice(1,notes.size())-1);
        notes.remove(note);
        decreaseSize();
        note.remove();
        mainMenu();
    }

    private void addNote() throws IOException {
        String title,context;
        Scanner input=new Scanner(System.in);
        System.out.print("Title: ");
        title=input.nextLine();
        System.out.print("Context: ");
        context=input.nextLine();
        Note note=new Note(size,title,context);
        notes.add(note);
        increaseSize();
        mainMenu();
    }

    private int getChoice(int min,int max){
        int choice;
        Scanner input=new Scanner(System.in);
        choice=input.nextInt();
        if(choice<min || max<choice){
            System.out.println("Invalid input, Try again");
            return getChoice(min,max);
        }
        return choice;
    }

    private void increaseSize() throws IOException {
        size++;
        FileWriter writer=new FileWriter("files/size.txt");
        writer.write(size+"");
        writer.close();
    }

    private void decreaseSize() throws IOException {
        size--;
        FileWriter writer=new FileWriter("files/size.txt");
        writer.write(size+"");
        writer.close();
    }
}