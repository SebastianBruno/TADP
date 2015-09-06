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
  end

  def self.guardar_objetos(*args)
    args.each do |objeto|
      if objeto.class != Regexp then
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

end