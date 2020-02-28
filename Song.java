public class Song{
	private String artist;
	private String title;

	public Song(String artist, String title){
		this.artist = artist;
		this.title = title;
	}

	public Song(){
		this.artist = "Jordan Reiser";
		this.title = "Future Millionaire";
	}

	public String getArtist(){
		return artist;
	}

	public String getTitle(){
		return title;
	}
}
