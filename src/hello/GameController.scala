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
* Cette classe est le controleur d'un jeu de Tic-Tac-Toe. Il gère les événement généré par l'interface.
* Il s'occupe de les gestions des tours et d'assigner les valeurs dans les cases respective. 
* Lorsque celui-ci est activé, il invoque l'intéligence artificielle.
* @author Guillaume Rochefort-Mathieu & Antoine Laplante
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
  
  
  /**
   * Fonction qui permet la gestion d'un tour d'un humain.
   * @param event Le clic du boutons
   */
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
   * @param index La position du boutons cliqué
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
   * @param nouvelleGrille La nouvelle grille de jeu
   * @param joueur Le joueur actif
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