class Transformations

  def initialize(args)
    @objects_with_methods = args
  end

  def inject(hash={})
    @objects_with_methods.each_pair do |key, methods|

      methods.each do |metodo|

        params = metodo.parameters.map(&:last)
        key.singleton_class.send(:alias_method, :metodoNuevo, metodo.name)
        key.singleton_class.send(:define_method, metodo.name) do |*args|

          hash.each do |key, value|
            args[params.index(key)] = value
          end

          key.send(:metodoNuevo,*args)

        end

      end

    end

  end

    #caca
  def redirect_to(instance)
    @objects_with_methods.each_pair do |objeto, metodos|
      objeto.class_eval do
        metodos.each do |metodo|
          objeto.define_singleton_method(metodo) do |*args|
            instance.send(metodo, *args)
          end
        end
      end
    end
  end
end