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
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

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

  var ai = false

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

  var joueurActif = ' '

  var listButton: List[jfxsc.Button] = List()

  @jfxf.FXML
  private var gridDelegate: jfxsl.GridPane = _
  private var grilleJeu: GridPane = _
  private var test: jfxsl.Pane = _
  @jfxf.FXML
  var etat: jfxsc.Label = _

  @jfxf.FXML
  private def actionCase(event: jfxe.ActionEvent) {
    println("")
    val btn: jfxsc.Button = event.getSource.asInstanceOf[jfxsc.Button]
    gestionTour((btn.getId() takeRight 1).toInt)
    if (ai) {
      gestionTourAI()
    }
  }

  /**
   * Fonction qui permet la gestion d'un tour d'un humain.
   */
  private def gestionTour(index: Int) {
    joueurActif = if (compteurTour % 2 == 0) joueurX else joueurO

    var partieTemp = utilTicTacToe.jouerTour(partie, joueurActif, index)

    updateGame(partieTemp, joueurActif)
  }

  /**
   * Fonction qui permet la gestion d'un tour d'un IA
   */
  private def gestionTourAI() {
    joueurActif = if (compteurTour % 2 == 0) joueurX else joueurO

    var partieTemp = utilTicTacToe.jouerTour(partie, joueurActif, utilTicTacToe.obtenirPositionAI(partie, joueurActif, joueurX))

    updateGame(partieTemp, joueurActif)
  }

  /**
   * Fonction qui permet de mettre à jour la vue
   */
  private def updateView() {
    for (a <- 0 to partie.size - 1) {
      grilleJeu.children(a).asInstanceOf[jfxsc.Button].setText(partie(a).toString())
    }

    var prochain = if (joueurActif == joueurO) joueurX else joueurO
    etat.setText("C'est le tour des " + prochain)
  }

  /**
   * Fonction qui permet de mettre à jour la partie
   */
  private def updateGame(nouvelleGrille: List[Char], joueur: Char) {
    if (!utilTicTacToe.partieEquals(partie, nouvelleGrille)) {
      partie = nouvelleGrille
      compteurTour = compteurTour + 1
      updateView()
    }

    if (compteurTour > 4 && compteurTour < 10) {
      if (utilTicTacToe.estGagnant(partie, joueur)) {
        var alert = new Alert(AlertType.Information)
        alert.setTitle("Partie Terminée")
        alert.setHeaderText("Le joueur " + joueur + " as gagné")
        alert.setContentText("Cliquer sur OK pour recommencer un partie")
        alert.showAndWait()
        initList()
      } else if (utilTicTacToe.gameOver(partie)) {
        var alert = new Alert(AlertType.Information)
        alert.setTitle("Partie Terminée")
        alert.setHeaderText("Partie null aucun joueur a gagné.")
        alert.setContentText("Cliquer sur OK pour recommencer un partie")
        alert.showAndWait()
        initList()
      }
    }
  }

  @jfxf.FXML
  private def restart(event: jfxe.ActionEvent) {
    initList()
  }

  @jfxf.FXML
  private def activerIA(event: jfxe.ActionEvent) {
    ai = !ai
  }

  def initialize(url: URL, rb: util.ResourceBundle) {
    grilleJeu = new GridPane(gridDelegate)
    for (a <- 0 to partie.size - 1) {
      grilleJeu.children(a).asInstanceOf[jfxsc.Button].setText(partie(a).toString())
    }
  }

  /**
   * Fonction qui permet de renitialiser la partie.
   */
  def initList() {
    etat.setText("La partie n'est pas commencé")
    partie = List('-', '-', '-', '-', '-', '-', '-', '-', '-')

    compteurTour = 0
    for (a <- 0 to partie.size - 1) {
      grilleJeu.children(a).asInstanceOf[jfxsc.Button].setText(partie(a).toString())
    }
  }
}