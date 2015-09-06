require 'rspec'
require_relative '../src/Aspects'

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
    Aspects.objetos.count.should eq(1)
    Aspects.objetos.first.should be(Aspects)
  end

  it 'Cuando le paso un regex, agrega su correspondiente clase a su array de objetos' do
    Aspects.on /Aspects/ do end
    Aspects.objetos.count.should eq(1)
    Aspects.objetos.first.should be(Aspects)
  end

  it 'Cuando le paso un regex que no matchea con nada, tira ArgumentError' do
    expect {
      Aspects.on /NoMatchea/,/TampocoMatchea/ do end
    }.to raise_error ArgumentError
  end

end