require_relative '../src/Conditions'

class Aspects

  def self.objetos
    @objetos
  end

  def self.on (*args, &bloque)
    @objetos = []

    #Guarda todos los objetos pasados por parametros
    guardar_objetos(*args)

    #Valida los parametros pasados
    raise ArgumentError if @objetos.count.eql? 0 or bloque.nil?

    conditions = Conditions.new(@objetos)

    #Ejecuta el proc en el contexto de Conditions
    conditions.instance_eval &bloque
  end

  def self.guardar_objetos(*args)
    args.each { |objeto|
      if objeto.class != Regexp
        #Si no es una expresion regular lo agrega directo
        @objetos << objeto
      else
        #Si es una expresion regular, busca en las constantes de Object, las Clases o Modulos que matcheen
        matcheadas = Object.constants.select { |symbol| symbol.to_s =~ objeto}

        #Si matchea la agrega solo si es una clase o un modulo
        matcheadas.each { |symbol|

          matcheado = Object.const_get(symbol)
          @objetos << matcheado if matcheado.is_a? Class or matcheado.is_a? Module
        }
      end
    }
  end
end