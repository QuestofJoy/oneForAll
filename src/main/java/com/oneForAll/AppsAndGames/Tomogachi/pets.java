package myapp.AppsAndGames.Tomogachi;

import java.util.List;

import javax.imageio.ImageIO;

import java.io.IOException;
import java.util.ArrayList;
import java.io.InputStream;

public class pets {
  private List<String> pets = new ArrayList<>();

  // Load pets
  private void loadPets() {
    try (InputStream ins = getClass().getResourceAsStream("/listOfPets.txt")) {
      if (ins != null) {
        String names = new String(ins.readAllBytes());
      }

    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("error: could not laod pets");
    }
  }
}
