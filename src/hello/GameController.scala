package hello

import java.net.URL
import java.util
import javafx.scene.{ control => jfxsc }
import javafx.scene.{ layout => jfxsl }
import javafx.{ event => jfxe }
import javafx.{ fxml => jfxf }
import scalafx.Includes._
import scalafx.scene.layout.GridPane
import javafx.scene.control.Button

/**
 * Example of a controlled initialized through FXML.
 *
 * When working with FXML, due to the nature of JavaFX FXMLLoader, we need to expose variables and methods that
 * FXMLLoader will be using with JavaFX signatures.
 *
 * The FXMLLoader injects JavaFX objects as values of member variables marked with annotation `@jfxf.FXML`.
 * We need to declare those variables using JavaFX types (not ScalaFX types).
 * We can use those variables directly or wrap them in ScalaFX objects.
 * Here, for the sake of illustration, we only wrap one variable `gridDelegate` (it is not strictly necessary).
 * The most convenient place to do wrapping is in the overloaded method `initialize`. It is executed after
 * FXMLLoader injects its objects.
 *
 * We can rely on ScalaFX "magic" to use ScalaFX methods on variables that were not explicitly wrapped.
 * All we need to do is to "summon the magic" using "import scalafx.Includes._".
 * This is demonstrated in method "handleClear" where we access properties on
 * JavaFX objects using ScalaFX way, no `get` or `set` involved.
 *
 * Methods annotated with `@jfxf.FXML`, that will be wired to event handlers by FLXMLoader.
 * They need to use JavaFX method signatures. This is illustrated in methods: `handleSubmit` and  `handleClear`.
 *
 * In the rest of the code we can use ScalaFX, for instance, to create more in the event handlers or bind
 * properties.
 *
 * @author Jarek Sacha
 */
class GameController extends jfxf.Initializable {

  var partie: List[Char] = List('-', '-', '-', '-', '-', '-', '-', '-', '-')

  var compteurTour: Int = 0
  
  val joueurX: Char = 'X'

  val joueurO: Char = 'O'

  private var listButton: List[jfxsc.Button] = _

  @jfxf.FXML
  private var gridDelegate: jfxsl.GridPane = _
  private var grilleJeu: GridPane = _

  @jfxf.FXML
  private def actionCase(event: jfxe.ActionEvent) {
    println(compteurTour)
    val btn: jfxsc.Button = event.getSource.asInstanceOf[jfxsc.Button]
    val joueurActif = if(compteurTour%2==0)joueurX else joueurO
    
    if(setValeur(joueurActif, (btn.getId() takeRight 1).toInt)){
      btn.setText(joueurActif.toString())
    }
    if (compteurTour > 4 && compteurTour < 10) {
      estGagnant(joueurActif)
    }
    //println(event.getSource.asInstanceOf[jfxsc.Button].getId() takeRight 1)
  }

  @jfxf.FXML
  private def handleClear(event: jfxe.ActionEvent) {

  }

  def initialize(url: URL, rb: util.ResourceBundle) {
    grilleJeu = new GridPane(gridDelegate)
  }

  def setValeur(joueur: Char, index: Int): Boolean = {
    var retour = false
    if(partie(index) == '-') {
      partie = partie.updated(index, joueur)
      compteurTour = compteurTour + 1
      retour = true
    }
    println(partie)
    return retour
  }

  def initList() {
    partie = List('-', '-', '-', '-', '-', '-', '-', '-', '-')
  }

  def estGagnant(joueur: Char) {

  }
}