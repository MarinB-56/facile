import { TransportTypePipePipe } from './transport-type-pipe.pipe';

describe('TransportTypePipePipe', () => {
  const pipe = new TransportTypePipePipe();

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('Transforms Train grande vitesse to train', () => {
    expect(pipe.transform("Train grande vitesse")).toBe('train');
  })

  it('Transorms TER / Intercités to train', () => {
    expect(pipe.transform("TER / Intercités")).toBe('train');
  })

  it('Transforms Autocar to bus', () => {
    expect(pipe.transform("Autocar")).toBe("bus");
  })

  it('Transforms Bateau to boat', () => {
    expect(pipe.transform("Bateau")).toBe("boat");
  })
});
