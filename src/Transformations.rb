require_relative '../src/Functions'

class Transformations
  include Funciones
  def initialize(args)
    @objects_with_methods = args
  end

  def inject(hash={})
    @objects_with_methods.each_pair do |key, methods|
      methods.each do |metodo|
        params = metodo.parameters.map(&:last)
        instance = obtenerClaseDeUnObjeto key
        instance.send(:alias_method, :metodoNuevo, metodo.name)
        instance.send(:define_method, metodo.name) do |*args|
          hash.each do |key, value|
            if value.class == Proc
              value = value.call self,metodo.name,args[params.index(key)]
            end
            args[params.index(key)] = value
          end
          self.send(:metodoNuevo,*args)
        end
      end
    end
  end

  def redirect_to(instance)
    mtds = []
    @objects_with_methods.each_pair do |objeto, metodos|
      metodos.each do |metodo|
        mtds = obtenerMetodosdeUnObjeto objeto
        if mtds.include?metodo.name #Validacion para que exista ese metodo para el nuevo objeto
          objeto.send(:define_method, metodo.name) do |*args|
            instance.method(metodo.name).call(*args)
          end
        end
      end
    end
  end

  def after(&bloque)
    @objects_with_methods.each_pair do |key, methods|
      methods.each do |metodo|
        params = metodo.parameters.map(&:last)
        instance = obtenerClaseDeUnObjeto key
        instance.send(:alias_method, :"#{metodo.name}_after", metodo.name)
        instance.send(:define_method, metodo.name) do |*args|
          self.send(:"#{metodo.name}_after", *args)
          self.instance_eval &bloque
        end
      end
    end
  end

  def instead_of(&bloque)
    @objects_with_methods.each_pair do |key, methods|
      methods.each do |metodo|
        instance = obtenerClaseDeUnObjeto key
        instance.send(:define_method, metodo.name) do |*args|
          self.instance_eval &bloque
        end
      end
    end
  end
end