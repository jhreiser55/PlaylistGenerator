import java.io.BufferedReader;
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
public class Main{
	
	private static DoubleList<Song> playlist;

	public static void main(String[] args) throws IOException {
		playlist = new DoubleList<Song>();

		System.out.println("*** Playlist Manager! ***");
		System.out.println("Commands:\nadd\nremove\ncount\nplay\nshuffle\nreverse\nquit\nhelp\nsave\nload\n");
		Scanner s = new Scanner(System.in);
		while(true){
			System.out.print(":");
			String command = s.next();
			
			//command to add a song to the playlist
			if(command.equals("add")){
				add(s);
			}

			//command to exit the program
			if(command.equals("quit") || command.equals("q")){
				break;
			}
			
			//command to see how many songs are in the playlist
			if(command.equals("count")){
				count();			
			}

			//command to print out the playlist
			if(command.equals("play")){
				play();
			}

			//command incase you forget the commands
			if(command.equals("help")){
				System.out.println();
				System.out.println("Commands:\nadd\nremove\ncount\nplay\nshuffle\nreverse\nquit\nhelp\nsave\nload\n");
				
			}

			//command to remove a song from the playlist
			if(command.equals("remove")){
				remove(s);
			}

			//command to reverse the playlist
			if(command.equals("reverse")){
				reverse();
			}

			//command to save the playlist to a file
			if(command.equals("save")){
				try{
					System.out.print("Enter File: ");
					String file = s.next();
					file += s.nextLine();
					PrintWriter w = new PrintWriter(new FileWriter(file));
					int count = playlist.count();
					//same thing as play() but instead of printing to screen
					//we are printing to the file.
					for(int i = 0; i < count; i++){
						Song A = playlist.getDataForward(i);
						w.println(A.getArtist() + " - " + A.getTitle());
					}
					w.close();
				}catch (IOException e){
					System.out.println("Couldn't save");
				}
				System.out.println();
			}

			//command to load a file in to the program
			if(command.equals("load")){
				try{
					System.out.println("Enter the file to load: ");
					String file = s.next();
					file += s.nextLine();
					BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
					String line = "";
					DoubleList<Song> newPlaylist = new DoubleList<Song>();
					//reading in a file then adding it to a DoubleList
					while((line = bufferedreader.readLine()) != null) {
						String[] getStuff = line.split(" - ");
						Song a = new Song(getStuff[0], getStuff[1]);
						newPlaylist.addEnd(a);
					}
					bufferedreader.close();
					playlist = newPlaylist;

				}catch (IOException e){
					System.out.println("Please try a different file.");
				}
				System.out.println();
			}

			//command to shuffle the playlist
			if(command.equals("shuffle")){
				for(int i = 0; i < 2; i++){
					DoubleList<Song> newPlaylist = new DoubleList<Song>();
					int count = playlist.count();
					while(count != 0){
						int random = (int)(Math.random() * (count));
						Song A = playlist.getDataForward(random);
						newPlaylist.addEnd(A);
						remove(A);
						count--;
					}
					playlist = newPlaylist;
				}
				play();
			}

			//STRICTLY CODE TO FORMAT THE PROGRAM
			String[] commands = {"save", "load", "add", "remove", "help", "shuffle", "play", "count", "reverse", "quit"};
			int i = -1;
			for(int j = 0; j < commands.length; j++){
				if(command.equals(commands[j])){
					i = 1;
				}
			}
			if(i == -1){
				System.out.println();
			}
		}
	}

	//takes the playlist and prints it out in reverse order
	public static void reverse(){
		int count = playlist.count();
		DoubleList<Song> newPlaylist = new DoubleList<Song>();
		for(int i = 0; i < count; i++){
			Song A = playlist.getDataBackwards(i);
			newPlaylist.addEnd(A);
		}
		playlist = newPlaylist;
		play();

	}
	
	//for shuffling purposes - I didnt want to rewrite the entire remove method
	//so i just copied it to take in different parameters. This remove method takes
	//in a song opposed to a scanner but with either method you will get the same 
	//output.
	public static void remove(Song B){
		String artist = B.getArtist();
		String title = B.getTitle();

		DoubleList<Song> newPlaylist = new DoubleList<Song>();
		int count = playlist.count();
		int placeHolder = -1;
		for(int i = 0; i < count; i++){
			Song A = playlist.getDataForward(i);
			if(A.getArtist().equals(artist) && A.getTitle().equals(title)){
				placeHolder = i;
			}
		}
		if(placeHolder == -1){
			System.out.println("Song does not exist.");
		}
		else{
			if(count == 1){
				playlist = null;
			}
			for(int i = 0; i < count; i++){
				if(i == placeHolder){
					//Do Nothing
				}
				else{
					Song A = playlist.getDataForward(i);
					newPlaylist.addEnd(A);
				}
			}
		}

		playlist = newPlaylist;
		
	}

	//for removing user inputted values
	public static void remove(Scanner s){
		System.out.print("Enter artist: ");
		String artist = s.next();
		artist += s.nextLine();
		System.out.print("Enter title: ");
		String title = s.nextLine();
		System.out.println();

		DoubleList<Song> newPlaylist = new DoubleList<Song>();
		int count = playlist.count();
		int placeHolder = -1;
		//searching the list to see if the song is there
		for(int i = 0; i < count; i++){
			Song A = playlist.getDataForward(i);
			if(A.getArtist().equals(artist) && A.getTitle().equals(title)){
				placeHolder = i;
			}
		}

		//indicates that the song isnt there
		if(placeHolder == -1){
			System.out.println("Song does not exist.");
		}
		else{
			if(count == 1){
				playlist = null;
			}

			//creating a new playlist without the users value
			for(int i = 0; i < count; i++){
				if(i == placeHolder){
					//Do Nothing
				}
				else{
					Song A = playlist.getDataForward(i);
					newPlaylist.addEnd(A);
				}
			}
		}

		playlist = newPlaylist;
		
	}

	//prints the contents of the playlist in "artist - title" format
	public static void play(){
		//couldnt think of a way to use the printforward
		//method so I just made my own specific method in doublelist
		int count = playlist.count();
		for(int i = 0; i < count; i++){
			Song A = playlist.getDataForward(i);
			System.out.println(A.getArtist() + " - " + A.getTitle());
		}
		System.out.println();
	}

	//gather user input to insert a new song to 
	//the end of the playlist.
	public static void add(Scanner s){
		System.out.print("Enter artist: ");
		String artist = s.next();
		artist += s.nextLine();
		System.out.print("Enter title: ");
		String title = s.nextLine();
		System.out.println();
		Song temp = new Song(artist,title);
		playlist.addEnd(temp);
	
	}

	//retireves the length of the playlist
	public static void count(){
		//hardcoded the count method to DoubleList class
		System.out.println(playlist.count());	
		System.out.println();		
	}
}
