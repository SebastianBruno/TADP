module Funciones
  def obtenerClaseDeUnObjeto arg
    if arg.is_a? Module or arg.is_a? Class
      arg
    else
      arg.singleton_class
    end
  end

  def obtenerMetodosdeUnObjeto arg
    if arg.is_a? Class or arg.is_a? Module
      arg.instance_methods(false)
    else
      arg.singleton_class.instance_methods(false)
    end
  end
end

