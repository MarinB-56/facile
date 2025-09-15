import { TimePipePipe } from './time-pipe.pipe';

describe('TimePipePipe', () => {
  const pipe = new TimePipePipe();

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('Affiche 6h53 pour la date 20250920T065300', () => {
    expect(pipe.transform('20250920T065300')).toBe('06h53');
  });

  it('Affiche 8h55 pour la date 20250920T085500', () => {
    expect(pipe.transform('20250920T085500')).toBe('08h55');
  });

  it('Affiche 12h58 pour la date 20250920T125800', () => {
    expect(pipe.transform('20250920T125800')).toBe('12h58');
  });

  it('Affiche 06h53 pour la date 20250920T065300', () => {
    expect(pipe.transform('20250920T060400')).toBe('06h04');
  });
});
