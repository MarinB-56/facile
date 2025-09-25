import { ConnectionPipePipe } from '../connection-pipe/connection-pipe.pipe';

describe('ConnectionPipePipe', () => {
  const pipe = new ConnectionPipePipe();

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it("affiche Direct, pour 0 correspondance", () => {
    expect(pipe.transform(0)).toBe("Direct");
  });

  it("affiche 2 correspondances, pour 2 correspondances", () => {
    expect(pipe.transform(2)).toBe("2 correspondances");
  });

  it("affiche 1 correspondance, pour 1 correspondance", () => {
    expect(pipe.transform(1)).toBe("1 correspondance");
  });

  it("affiche NaN en cas de problème de correspondance (null ou undefined)", () => {
    expect(pipe.transform(-1)).toBe("NaN");
  });
});
