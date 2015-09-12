require 'rspec'
require_relative '../src/transformers/Transformations'

describe Transformations do

  it 'must replace value in parameter' do

    prueba = Prueba.new

    transformer = Transformations.new({prueba => [prueba.singleton_class.instance_method(:metodo)]})

    transformer.inject({:p2 => "pepe", :p1 => "La_Rola"})

    prueba.metodo("s","2222222")
  end

  it 'Test del redirect_to' do
    class A
      def saludar(x)
        "Hola " + x
      end
    end

    class B
      def saludar(x)
        "Adiosin " + x
      end
    end

    Aspects.on A do
      transform(where name(/saludar/)) do
        redirect_to(B.new)
      end
    end

    expect(A.new.saludar("Mundo")).to eq("Adiosin Mundo")
  end

end

class Prueba

  def metodo(p1,p2)
    puts(p1 + " - " + p2)
  end

end