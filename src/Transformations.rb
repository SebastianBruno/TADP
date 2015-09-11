class Transformations

  def initialize(args)
    @objects_with_methods = args
  end

  def inject(hash={})
    @objects_with_methods.parameters.select do |parameter|
      parameter = hash.keys.first
    end
  end

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