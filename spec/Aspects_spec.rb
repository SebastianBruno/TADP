require 'rspec'
require_relative '../src/Aspects'
require_relative '../src/TestClass'

describe Aspects do

  it 'Cuando le paso 0 parametros tira ArgumentError' do
    expect {
      Aspects.on
    }.to raise_error ArgumentError
  end

  it 'Cuando no le paso un block tira ArgumentError' do
    expect {
      Aspects.on Aspects
    }.to raise_error ArgumentError
  end

  it 'Cuando le paso una clase la agrega a su array de objetos' do
    Aspects.on Aspects do end
    expect(Aspects.objetos.count).to eq(1)
    expect(Aspects.objetos.first).to eq(Aspects)
  end

  it 'Cuando le paso un regex, agrega su correspondiente clase a su array de objetos' do
    Aspects.on /Aspects/ do end
    expect(Aspects.objetos.count).to eq(1)
    expect(Aspects.objetos.first).to eq(Aspects)
  end

  it 'Cuando le paso un regex que no matchea con nada, tira ArgumentError' do
    expect {
      Aspects.on /NoMatchea/,/TampocoMatchea/ do end
    }.to raise_error ArgumentError
  end

  it 'Cuando le paso un regex que no matchea con nada y otro que si, agrega el que si a la lista' do
      Aspects.on /NoMatchea/, /Aspects/ do end
      expect(Aspects.objetos.count).to eq(1)
      expect(Aspects.objetos.first).to eq(Aspects)

      Aspects.on /Aspects/, /NoMatchea/ do end
      expect(Aspects.objetos.count).to eq(1)
      expect(Aspects.objetos.first).to eq(Aspects)
  end

  it 'Cuando le paso una regex que matchea con dos metodos, que me los devuelva' do
      metodos = Aspects.on /TestClass/ do
        where name(/_metodo/)
      end
      expect(metodos.count).to eq(2)
  end

  it 'Cuando le paso nil a name, devuelve ArgumentError' do
    expect {
      Aspects.on /TestClass/ do
        where name(nil)
      end
    }.to raise_error ArgumentError
  end

  it 'Obtener un metodo con dos parametros obligratorios de TestClass' do
    metodos = Aspects.on TestClass do
      where has_parameters(2, mandatory)
    end

    expect(metodos.count).to eq(1)
    expect(metodos[0]).to eq(:metodo_con_dos_parametros)
  end

  it 'Obtener un metodo con tres parametros obligatorios de TestClass' do
    metodos = Aspects.on TestClass do
      where has_parameters(3, mandatory)
    end

    expect(metodos.count).to eq(1)
    expect(metodos[0]).to eq(:metodo_con_tres_parametros)
  end

  it 'Obtener dos metodos que no reciben parametros de TestClass' do
    metodos = Aspects.on TestClass do
      where has_parameters(0)
    end

    expect(metodos.count).to eq(2)
    expect(metodos[0]).to eq(:un_metodo)
    expect(metodos[1]).to eq(:otro_metodo)
  end

  it 'Obtener un metodo con tres parametros opcionales de TestClass' do
    metodos = Aspects.on TestClass do
      where has_parameters(3, optional)
    end

    expect(metodos.count).to eq(1)
    expect(metodos[0]).to eq(:metodo_con_tres_parametros_opcionales)
  end

end