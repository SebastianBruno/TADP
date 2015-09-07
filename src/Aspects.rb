class Aspects

  #Todos los objetos a donde aplicara este aspect
  @@objetos

  def self.objetos
    @@objetos
  end

  def self.on (*args,&bloque)
    @@objetos = []

    #Guarda todos los objetos pasados por parametros
    guardar_objetos(*args)

    #Valida los parametros pasados
    raise ArgumentError if objetos.count.eql? 0 or bloque.nil?

    #Ejecuta el proc en este contexto
    instance_eval &bloque
  end

  def self.guardar_objetos(*args)
    args.each do |objeto|
      if objeto.class != Regexp
        #Si no es una expresion regular lo agrega directo
        objetos << objeto
      else
        #Si es una expresion regular, busca en las constantes de Object, las Clases o Modulos que matcheen
        Object.constants.each do |symbol|
          if !(symbol.to_s =~ objeto).nil? then
            #Si matchea la agrega solo si es una clase o un modulo
            @matcheado = Object.const_get(symbol)
            objetos << @matcheado if @matcheado.class.to_s.eql?('Class') or @matcheado.class.to_s.eql?('Module')
          end
        end
      end
    end
  end

def self.has_parameters(amount, status = 'everything')
    if status == 'mandatory'
    
    #Antes de los parameters habría que meter de alguna forma al metodo que va a llamar a la condicion
      parameters.select do |param| param.first == :req end.size == amount
    elsif status == 'optional'
      parameters.select do |param| param.first == :opt end.size == amount
    elsif status == 'everything'
      parameters.size == amount
    end
  end

  def self.cumple_condiciones(metodo, *condiciones)
    condiciones.all? { |condicion| metodo condicion}
  end

  def self.where(*condiciones)
    if(obj.class == Class)
      @metodos = obj.instance_methods
    else
      @metodos = obj.methods
    end
    
    @metodos.select do |metodo| cumple_condiciones(metodo, condiciones) end
  end

end

end
