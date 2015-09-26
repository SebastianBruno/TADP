module Funciones
  def obtenerClaseDeUnObjeto arg
    if arg.class == Module or arg.class == Class
      arg
    else
      arg.singleton_class
    end
  end

  def obtenerMetodosdeUnObjeto arg
    if arg.class == Class or arg.class == Module
      arg.instance_methods(false)
    else
      arg.singleton_class.instance_methods(false)
    end
  end
end

