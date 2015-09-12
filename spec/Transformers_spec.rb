require 'rspec'
require_relative '../src/Aspects'
require_relative '../src/MiClase'
require_relative '../src/transformers/Transformations'

describe Transformations do

  it 'must replace value in parameter' do

    prueba = Prueba.new

    transformer = Transformations.new({prueba => [prueba.singleton_class.instance_method(:metodo)]})

    transformer.inject({:p2 => "cambiame2", :p1 => "cambiame1"})

    expect(prueba.metodo("s","2222222")).to eq("cambiame1 - cambiame2")
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

  it 'test del after' do
    Aspects.on MiClase do
      transform(where name(/m2/)) do
        after do |instance, *args|
          if @x > 100
            2 * @x
          else
            @x
          end
        end
      end
    end
    instancia = MiClase.new
    expect(instancia.m2(10)).to eq 10

    expect(instancia.m2(200)).to eq 400
  end

  it 'test del instead_of' do
    Aspects.on MiClase do
      transform(where name(/m3/)) do
        instead_of do |instance, *args|
          @x = 123
        end
      end
    end

    instancia = MiClase.new
    instancia.m3(10)
    expect(instancia.x).to eq 123
  end

end

class Prueba

  def metodo(p1,p2)
    return (p1 + " - " + p2)
  end

end