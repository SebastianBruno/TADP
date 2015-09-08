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

  def has_parameters(amount, status = 'everything')
    if status == 'mandatory'
      return @metodos.select do |metodo| method(metodo).parameters.select do |param| param.first == :req end.size == amount end
    elsif status == 'optional'
      return @metodos.select do |metodo| method(metodo).parameters.select do |param| param.first == :opt end.size == amount end
    elsif status == 'everything'
      return @metodos.select do |metodo| method(metodo).parameters.size == amount end
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