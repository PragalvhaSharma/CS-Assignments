public class Sample {

    public static void main (String[] args) {
	try {
	    SoundPlayer player = new SoundPlayer();
	    player.play("bin/spring.wav");

	    PictureViewer viewer = new PictureViewer();
	    viewer.show("bin/flower.jpg");
	    
	    ShowHTML browser = new ShowHTML();
	    browser.show("bin/Matrices.html");
	}
	catch (MultimediaException e) {
	    System.out.println(e.getMessage());
	}
    }
}
