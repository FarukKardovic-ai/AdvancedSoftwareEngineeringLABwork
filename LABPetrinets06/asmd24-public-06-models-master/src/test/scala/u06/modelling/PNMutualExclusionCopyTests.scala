package u06.modelling

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.*

import u06.utils.MSet
import scala.u06.examples.PNMutualExclusionCopy.*

class PNMutualExclusionCopyTests extends AnyFunSuite {

  test("Initial marking should have two tokens in N") {
    val initial = MSet(N, N)
    assert(initial(N) == 2)
  }

  test("The Petri net should produce at least one path of length 3") {
    val paths = pnME.paths(MSet(N, N), 3).toList
    assert(paths.nonEmpty)
  }

  test("All paths should start from the initial marking (N, N)") {
    val paths = pnME.paths(MSet(N, N), 3).toList
    paths.foreach { path =>
      assert(path.head == MSet(N, N))
    }
  }

  test("Mutual exclusion: at most one token in C at any time") {
    val paths = pnME.paths(MSet(N, N), 7).toList
    val violation = paths.exists { path =>
      path.exists(marking => marking(C) > 1)
    }
    assert(!violation, "Mutual exclusion violated: more than one token in C")
  }
}
