#language: es
@testfeature
Característica: Invalid session

  @testSession

  Escenario: Validación del Precio de un Producto
    Dado estoy en la página de la tienda
    Y me logueo con mi usuario "keliza.vargas94@gmail.com" y clave "AMYLI1725"
    Entonces valido que la autenticacion sea exitosa