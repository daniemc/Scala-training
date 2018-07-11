package co.com.s4n.testingscala.Modeling

import org.scalatest._;

class Modeling extends FunSuite {
  
  trait Color 
    case class Negro() extends Color
    case class Rojo() extends Color
    case class Verde() extends Color
  
  trait Marca 
    case class Renault() extends Marca
    case class Chevrolet() extends Marca
    case class Kia() extends Marca

  trait Vehiculo {
    val color: Color
    val marca: Marca
  }

  case class Carro(color: Color, marca: Marca) extends Vehiculo

  object tallerPintura {
    def pintar(carro: Carro, nuevoColor: Color): Carro = {
      Carro(nuevoColor, carro.marca)
    }
  }

  test("puedo pintar mi carro carro") {
    import tallerPintura._

    val miCarro = Carro(Rojo(), Renault())    
    val miCarroPintado = pintar(miCarro, Negro())

    assert(Negro() == miCarroPintado.color)
    assert(Renault() == miCarroPintado.marca)

  }

}