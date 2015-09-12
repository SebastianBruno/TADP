class Transformations

  def initialize(args)
    @objects_with_methods = args
  end

  def inject(hash={})
    @objects_with_methods.each_pair do |key, methods|
      methods.each do |metodo|
        params = metodo.parameters.map(&:last)
        if key.class == Module or key.class == Class
          instance = key
        else
          instance = key.singleton_class
        end
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

      if objeto.class == Class or objeto.class == Module
        mtds = objeto.instance_methods(false)
      else
        mtds = objeto.singleton_class.instance_methods(false)
      end

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
        if key.class == Module or key.class == Class
          instance = key
        else
          instance = key.singleton_class
        end

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
        params = metodo.parameters.map(&:last)
        if key.class == Module or key.class == Class
          instance = key
        else
          instance = key.singleton_class
        end
        instance.send(:define_method, metodo.name) do |*args|
          self.instance_eval &bloque
        end
      end
    end
  end
end