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

  var listButton: List[jfxsc.Button] = List()

  @jfxf.FXML
  private var gridDelegate: jfxsl.GridPane = _
  private var grilleJeu: GridPane = _
  private var test: jfxsl.Pane = _
  @jfxf.FXML
  var etat: jfxsc.Label = _

  @jfxf.FXML
  private def actionCase(event: jfxe.ActionEvent) {
    println(compteurTour)
    val btn: jfxsc.Button = event.getSource.asInstanceOf[jfxsc.Button]
    listButton = listButton :+ btn
    var joueurActif = if (compteurTour % 2 == 0) joueurX else joueurO
    if (ai) {
      joueurActif = joueurX
    }

    if (setValeur(joueurActif, (btn.getId() takeRight 1).toInt)) {
      btn.setText(joueurActif.toString())
    }

    if (compteurTour > 4 && compteurTour < 10) {
      if (estGagnant(joueurActif)) {
        println("tu as gagner")
        initList()
      } else {
        if (gameOver()) {
          println("partie null et fini")
          initList()
        }
      }
    }

    if (ai) {
      println("tour ai")
      tourAi();
    }

    var prochain = if (joueurActif == joueurO) joueurX else joueurO
    etat.setText("C'est le tour des " + prochain)
  }

  def tourAi() {
    var aJoue = false
    var joueurActif = joueurO

    for (a <- 0 to 7) {
      for (b <- 0 to 2) {
        if (partie(cGagnante(a)(b)) == '-' && !aJoue) {
          partie = partie.updated(cGagnante(a)(b), joueurActif)
          if (estGagnant(joueurActif)) {
            aJoue = true
            partie = partie.updated(cGagnante(a)(b), '-')
            setValeur(joueurActif, cGagnante(a)(b))
          } else {
            partie = partie.updated(cGagnante(a)(b), '-')
          }
        }
      }
    }

    var positionPossible: List[Int] = List()

    if (!aJoue) { // Si l'adversaire vas gagner ce tour que IA n'as pas joué
      for (a <- 0 to 7) {
        for (b <- 0 to 2) {
          if (partie(cGagnante(a)(b)) == '-' && !aJoue) {
            partie = partie.updated(cGagnante(a)(b), joueurX)
            if (estGagnant(joueurX)) {
              aJoue = true
              partie = partie.updated(cGagnante(a)(b), '-')
              setValeur(joueurActif, cGagnante(a)(b))
              grilleJeu.children(cGagnante(a)(b)).asInstanceOf[jfxsc.Button].setText(joueurActif.toString())
            } else {
              partie = partie.updated(cGagnante(a)(b), '-')
            }
          }
        }
      }
    }

    if (!aJoue) { // Si aucun joeur ne vas ganger ce tour et que IA n'as pas joué
      for (a <- 0 to 8) {
        if (partie(a) == '-') {
          positionPossible = positionPossible :+ a
        }
      }
      println("position possible = " + positionPossible)

      val positionJoue = scala.util.Random.nextInt(positionPossible.size)
      println("position possible = " + positionJoue)
      setValeur(joueurActif, positionJoue)
      grilleJeu.children(positionJoue).asInstanceOf[jfxsc.Button].setText(joueurActif.toString())
    }

    if (compteurTour > 4 && compteurTour < 10) {
      if (estGagnant(joueurActif)) {
        println("IA as gagner")
        initList()
      } else {
        if (gameOver()) {
          println("partie null et fini")
          initList()
        }
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

  /**
   * Fonction qui permet de renitialiser la partie.
   */
  def initList() {
    etat.setText("La partie n'est pas commencé")
    partie = List('-', '-', '-', '-', '-', '-', '-', '-', '-')

    compteurTour = 0
    for (a <- 0 to grilleJeu.children.size() - 1) {
      grilleJeu.children(a).asInstanceOf[jfxsc.Button].setText(" ")
    }
    listButton = List()
  }

  def gameOver(): Boolean =
    {
      var retour = false

      if (compteurTour == 9) {
        retour = true
      }

      return retour
    }

  def estGagnant(joueur: Char): Boolean = {
    var retour = false

    for (a <- 0 to 7) {
      var nmb = 0
      for (b <- 0 to 2) {
        if (partie(cGagnante(a)(b)) == joueur) {
          nmb = nmb + 1
          if (nmb == 3) {
            retour = true
          }
        }
      }
    }
    return retour
  }
}