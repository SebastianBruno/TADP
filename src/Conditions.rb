class Conditions

  def initialize(objetos)
    @objetos = objetos
    @metodos = []
    @objetos.each do |objeto|
      @metodos.concat(objeto.instance_methods(false))
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
      metodos = objeto.instance_methods(false)

      result.concat(metodos.select { |metodo|
        objeto.new.method(metodo).parameters.select { |parameter|
          block.call(criteria, parameter)
        }.count == amount
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