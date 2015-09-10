class Conditions

  def initialize(objetos)
    @objetos = objetos
    @metodos_parciales = [] #Array de UnboundMethods
    @objects_with_methods = {} #hash de metodos

    #[ Clase : [ :metodo1, :metodo2] , objeto : [ :metodo1] ]

    @objetos.each do |objeto|
      puts(objeto)
       if objeto.class == Class or objeto.class == Module
         objeto.instance_methods(false).each do |met|
           @metodos_parciales << objeto.instance_method(met)
         end
       else
         objeto.singleton_class.instance_methods(false) + objeto.class.instance_methods(false).each do |met|
           @metodos_parciales << objeto.singleton_class.instance_method(met)
         end
       end
       @objects_with_methods.merge({objeto => @metodos_parciales})
       @metodos_parciales = []
    end
  end

  def name(regex)
    raise ArgumentError if regex.nil? or regex.eql? ''
    metodos_matcheados = []
    @objects_with_methods.values.flatten.each { |metodo|
        metodos_matcheados << metodo.name if metodo.name =~ regex
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

    result.concat(@objects_with_methods.values.flatten.select { |metodo|
      metodo.parameters.select { |parameter|
          block.call(criteria, parameter)
        }.count == amount
    })

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

  #args = [[metodos_condicion1],[metodos_condicion2],[metodos_condicion3]]

  def where(*args)
    raise ArgumentError if args.nil? or args.count.eql? 0
    metodos_totales = args[0]
    args.each { |metodos_condicion|
      metodos_totales = metodos_totales & metodos_condicion
    }
    return metodos_totales
  end

  def neg(arg)
    @objects_with_methods.values.flatten - arg
  end

  def transform(arg)

  end

  def inject(hash={})
    hash.each_pair
  end

end