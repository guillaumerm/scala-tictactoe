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

  val cGagnante: List[List[Int]] = List(
    List(0, 1, 2),
    List(3, 4, 5),
    List(6, 7, 8),
    List(0, 3, 6),
    List(1, 4, 7),
    List(2, 5, 8),
    List(0, 4, 8),
    List(2, 4, 6))

  var partie: List[Char] = List('-', '-', '-', '-', '-', '-', '-', '-', '-')

  var compteurTour: Int = 0

  val joueurX: Char = 'X'

  val joueurO: Char = 'O'

  var listButton: List[jfxsc.Button] = List()

  @jfxf.FXML
  private var gridDelegate: jfxsl.GridPane = _
  private var grilleJeu: GridPane = _

  @jfxf.FXML
  private def actionCase(event: jfxe.ActionEvent) {
    println(compteurTour)
    val btn: jfxsc.Button = event.getSource.asInstanceOf[jfxsc.Button]
    listButton = listButton :+ btn
    val joueurActif = if (compteurTour % 2 == 0) joueurX else joueurO

    if (setValeur(joueurActif, (btn.getId() takeRight 1).toInt)) {
      btn.setText(joueurActif.toString())
    }
    if (compteurTour > 4 && compteurTour < 10) {
      if(estGagnant(joueurActif))
      {
        println("tu as gagner")
        initList()
      }
    }
  }

  @jfxf.FXML
  private def handleClear(event: jfxe.ActionEvent) {

  }

  def initialize(url: URL, rb: util.ResourceBundle) {
    grilleJeu = new GridPane(gridDelegate)
  }

  def setValeur(joueur: Char, index: Int): Boolean = {
    var retour = false
    if (partie(index) == '-') {
      partie = partie.updated(index, joueur)
      compteurTour = compteurTour + 1
      retour = true
    }
    println(partie)
    return retour
  }

  def initList() {
    
    partie = List('-', '-', '-', '-', '-', '-', '-', '-', '-')
    
    compteurTour = 0
    for (a <- 0 to (listButton.size - 1)) {
      listButton(a).setText(" ")
    }
    listButton = List()
  }

  def estGagnant(joueur: Char): Boolean = {
    var retour = false

    for (a <- 0 to 7) {
      var nmb = 0
      for (b <- 0 to 2) {
        if (partie(cGagnante(a)(b)) == joueur)
        {
          nmb = nmb + 1
          if(nmb == 3){
            retour = true
          }
        }
      }
    }
    return retour
  }
}