require 'rspec'
require_relative '../src/Aspects'
require_relative '../src/Conditions'
require_relative '../src/TestClass'

describe Conditions do

  it 'Cuando el where está vacío, tira ArgumentError' do
    conditions = Conditions.new([TestClass])
    expect {
      conditions.where
    }.to raise_error ArgumentError
  end

  it 'Cuando el name está vacío, tira ArgumentError' do
    conditions = Conditions.new([TestClass])
    expect {
      conditions.name(nil)
    }.to raise_error ArgumentError
  end

  it 'Cuando le paso varias listas de metodos al where, me devuelve la intereseccion de todas' do
    methods = TestClass.methods(false)
    conditions = Conditions.new([TestClass])
    expect(conditions.where [methods[0]], [methods[0], methods[1]]).to eq([methods[0]])
  end
end