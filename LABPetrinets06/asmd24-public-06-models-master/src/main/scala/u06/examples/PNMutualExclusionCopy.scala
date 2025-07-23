package scala.u06.examples

export u06.modelling.PetriNet
import u06.utils.MSet

object PNMutualExclusionCopy:

  enum Place:
    case N, T, C , A, B, E, F
    
  export Place.*
  export u06.modelling.PetriNet.*
  export u06.modelling.SystemAnalysis.*
  export u06.utils.MSet

  // DSL-like specification of a Petri Net
  def pnME = PetriNet[Place](
    MSet(N) ~~> MSet(T),
    MSet(T) ~~> MSet(C) ^^^ MSet(C),
    MSet(C) ~~> MSet(A),
    MSet(A) ~~> MSet(B),
    MSet(B) ~~> MSet(E),
    MSet(E) ~~> MSet(F),
    MSet(F) ~~> MSet()
  ).toSystem

@main def mainPNMutualExclusion =
  import PNMutualExclusionCopy.*
  // example usage
  println(pnME.paths(MSet(N,N),7).toList.mkString("\n"))
