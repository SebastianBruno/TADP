class Aspects

  #Todos los objetos a donde aplicara este aspect
  @@objetos = []

  def self.objetos
    @@objetos
  end

  def self.on (*args,&bloque)
    #Chequea que se hayan pasado parametros
    raise ArgumentError if args.count == 0 or bloque.nil?

    guardarObjetos(args)

    #Valida los parametros
  end


  #Guarda todos los objetos pasados por parametros
  def self.guardarObjetos(*args)
    args.each do |objeto|
      if objeto.class != Regexp then
        #Si no es una expresion regular la agrega directo
        objetos << objeto
      else
        #Si es una expresion regular, busca en las constantes de Object, las Clases o Modulos que matcheen
        Object.constants.each do |symbol|
          if !(symbol.to_s =~ objeto).nil? then
            #Si matchea la agrega solo si es una clase o un modulo
            @matcheado = Object.const_get(symbol)
            objetos << @matcheado if @matcheado.class.equal?('Class') or @matcheado.class.equal?('Module')
          end
        end
      end
    end
  end

end