import { DestinationPipePipe } from './destination-pipe.pipe';

describe('DestinationPipePipe', () => {
  const pipe = new DestinationPipePipe();

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('should transform Quiberon (Quiberon) to Belle-île-en-mer', () => {
    expect(pipe.transform('Quiberon (Quiberon)')).toBe("Belle-île-en-mer");
  })

  it('should transform Toulon (83000-83200) to Toulon', () => {
    expect(pipe.transform("Toulon (83000-83200)")).toBe("Toulon");
  })

  it('should transorm Toulon (Toulon) to Toulon', () => {
    expect(pipe.transform('Toulon (Toulon)')).toBe("Toulon");
  })

  it('should transorm Toulon Sainte-Musse (Toulon) to Toulon Sainte-Musse', () => {
    expect(pipe.transform('Toulon Sainte-Musse (Toulon)')).toBe("Toulon Sainte-Musse");
  })

  it('sould transform Paris Est (Paris) to Paris Est', () => {
    expect(pipe.transform('Paris Est (Paris)')).toBe('Paris Est');
  })
});
