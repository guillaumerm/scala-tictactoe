package hello

import java.net.URL
import java.util
import javafx.scene.{ control => jfxsc }
import javafx.scene.{ layout => jfxsl }
import javafx.{ event => jfxe }
import javafx.{ fxml => jfxf }
import scalafx.Includes._
import scalafx.scene.layout.GridPane
import scalafx.scene.Node
import javafx.scene.control.Button
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import javafx.event.EventHandler
import scalafx.event.ActionEvent

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

  var ensemblePartie: List[List[Char]] = _

  var indexPartieActive: Int = -1

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
    var partie = ((btn.getId() takeRight 2) take 1).toInt
    var indexPartie = ((btn.getId() takeRight 2) takeRight 1).toInt

    if (compteurTour == 0 || partie == indexPartieActive) {
      indexPartieActive = indexPartie
      gestionTour(partie, indexPartie)
      if (ai) {
        gestionTourAI(indexPartieActive)
      }
    } else {
      println("Vous n'avez pas joué dans la bonne case!!!")
    }
    //println(ensemblePartie)
  }

  /**
   * Fonction qui permet la gestion d'un tour d'un humain.
   * @param index La position du boutons cliqué
   */
  private def gestionTour(indexPartie: Int, indexPosition: Int) {
    joueurActif = if (compteurTour % 2 == 0) joueurX else joueurO

    var temp = ensemblePartie.updated(indexPartie, utilTicTacToe.jouerTour(ensemblePartie(indexPartie), joueurActif, indexPosition))

    updateGame(temp, joueurActif)
  }

  /**
   * Fonction qui permet la gestion d'un tour d'un IA
   */
  private def gestionTourAI(indexPartie: Int) {
    joueurActif = if (compteurTour % 2 == 0) joueurX else joueurO

    var position = utilTicTacToe.obtenirPositionAI(indexPartieActive, ensemblePartie, joueurActif, joueurX)
    println(ensemblePartie)
    var temp = ensemblePartie.updated(indexPartie, utilTicTacToe.jouerTour(partie, joueurActif, position))
    
    println(temp)
    
    indexPartieActive = position

    updateGame(temp, joueurActif)
  }

  /**
   * Fonction qui permet de mettre à jour la vue
   */
  private def updateView() {
    for (a <- 0 to ensemblePartie.size - 1) {
      for (b <- 0 to ensemblePartie(a).size - 1) {
        if (grilleJeu.children(a).isInstanceOf[jfxsl.GridPane]) {
          var temp = grilleJeu.children(a).asInstanceOf[jfxsl.GridPane];
          temp.children(b).asInstanceOf[jfxsc.Button].setText(ensemblePartie(a)(b).toString())
          if (a == indexPartieActive) {
            temp.children(b).asInstanceOf[jfxsc.Button].setStyle("-fx-background-color: white")
          } else {
            temp.children(b).asInstanceOf[jfxsc.Button].setStyle("")
          }
        }
      }
    }
    var prochain = if (joueurActif == joueurO) joueurX else joueurO
    etat.setText("C'est le tour des " + prochain)
  }

  /**
   * Fonction qui permet de mettre à jour la partie
   * @param nouvelleGrille La nouvelle grille de jeu
   * @param joueur Le joueur actif
   */
  private def updateGame(nouvelleGrille: List[List[Char]], joueur: Char) {
    if (!utilTicTacToe.partieEquals(ensemblePartie, nouvelleGrille)) {
      ensemblePartie = nouvelleGrille
      compteurTour = compteurTour + 1
      updateView()
    }

    if (compteurTour > 4 && compteurTour < 10) {
      if (utilTicTacToe.estGagnant(ensemblePartie, joueur)) {
        var alert = new Alert(AlertType.Information)
        alert.setTitle("Partie Terminée")
        alert.setHeaderText("Le joueur " + joueur + " as gagné")
        alert.setContentText("Cliquer sur OK pour recommencer un partie")
        alert.showAndWait()
        initList()
      } else if (utilTicTacToe.gameOver(ensemblePartie)) {
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
    initList()
    updateView()
  }

  /**
   * Fonction qui permet de renitialiser la partie.
   */
  def initList() {
    indexPartieActive = -1
    etat.setText("La partie n'est pas commencé")
    partie = List('-', '-', '-', '-', '-', '-', '-', '-', '-')

    compteurTour = 0
    ensemblePartie = utilTicTacToe.initList(List[List[Char]]())
    updateView()
  }
}