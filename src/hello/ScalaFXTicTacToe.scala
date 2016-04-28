package hello

import java.io.IOException
import javafx.{fxml => jfxf}
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene

object ScalaFXTicTacToe extends JFXApp {

  val resource = getClass.getResource("AdoptionForm.fxml")
  if (resource == null) {
    throw new IOException("Cannot load resource: AdoptionForm.fxml")
  }
  
  val root: jfxs.Parent = jfxf.FXMLLoader.load(resource)

  stage = new PrimaryStage() {
    title = "FXML GridPane Demo"
    scene = new Scene(root)
  }
}