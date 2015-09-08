class Conditions

  def initialize(objetos)
    @objetos = objetos
  end

  def name(regex)
    raise ArgumentError if regex.nil? or regex.eql? ''
    metodos_matcheados = []
    @objetos.each { |objeto|
      objeto.instance_methods(false).each { |metodo|
        metodos_matcheados << metodo if metodo =~ regex
      }
    }

    return metodos_matcheados
  end

  def where(*args)
    raise ArgumentError if args.nil? or args.count.eql? 0

    metodos_totales = args[0]

    args.each { |metodos_condicion|
      metodos_totales = metodos_totales & metodos_condicion
    }

    return metodos_totales
  end


end