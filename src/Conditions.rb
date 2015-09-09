class Conditions

  def initialize(objetos)
    @objetos = objetos
    @metodos = []

    #Guarda todos los metodos en el array
    @objetos.each do |objeto|
      if objeto.class == Class or objeto.class == Module
        @metodos.concat(objeto.instance_methods(false))
      else
        @metodos.concat(objeto.class.instance_methods(false))
      end
    end

  end

  def name(regex)
    raise ArgumentError if regex.nil? or regex.eql? ''
    metodos_matcheados = []
    @metodos.each { |metodo|
        metodos_matcheados << metodo if metodo =~ regex
      }
    return metodos_matcheados
  end

  def optional
    return [:opt]
  end

  def mandatory
    return [:req]
  end

  def any
    return [:opt, :req, :bloc, :rest]
  end

  def find_methods_with_criteria(amount, criteria, &block)
    result = []
    @objetos.each { |objeto|
      if objeto.class == Class
        metodos = objeto.instance_methods(false)
        instancia = objeto.new
      elsif objeto.class == Module
        metodos = objeto.instance_methods(false)
        instancia = objeto
      else
        metodos = objeto.class.instance_methods(false)
        instancia = objeto
      end

      result.concat(metodos.select { |metodo|
          if objeto.class == Module
            instancia.instance_method(metodo).parameters.select { |parameter|
              block.call(criteria, parameter)
            }.count == amount
          else
            instancia.method(metodo).parameters.select { |parameter|
              block.call(criteria, parameter)
            }.count == amount
          end
      })
    }

    return result
  end


  def has_parameters(amount, criteria = nil)
    if(criteria.nil?)
      criteria = any()
    end

    if(criteria.class == Regexp)
      return find_methods_with_criteria amount, criteria do |criteria, parameter|
        !(criteria =~ parameter[1]).nil?
      end
    end

    return find_methods_with_criteria amount, criteria do |criteria, parameter|
        criteria.include? parameter.first
    end

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