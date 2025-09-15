import { DurationPipePipe } from './duration-pipe.pipe';

describe('DurationPipePipe', () => {
  const pipe = new DurationPipePipe();

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('Affiche 0h pour 0 secondes', () => {
    expect(pipe.transform(0)).toBe('0 min');
  });

  it('Affiche 1h pour 3600 secondes', () => {
    expect(pipe.transform(3600)).toBe('1h');
  });

  it('Affiche 42 min pour 2542 secondes', () => {
    expect(pipe.transform(2542)).toBe('42 min');
  });

  it('Affiche 59 min pour 3540 secondes', () => {
    expect(pipe.transform(3540)).toBe('59 min');
  });

  it('Affiche 2h30 pour 9000 secondes', () => {
    expect(pipe.transform(9000)).toBe('2h30');
  });

  it('Affiche 0 min pour 30 secondes', () => {
    expect(pipe.transform(30)).toBe('0 min');
  });

  it('Affiche 2h05 pour 7500 secondes', () => {
    expect(pipe.transform(7500)).toBe('2h05');
  });

});


    // 1h0 => 1h
    // 0h42 => 42 min
    // 2h34 => 2h34
