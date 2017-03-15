library IEEE;
use IEEE.STD_LOGIC_1164.all;
--use IEEE.NUMERIC_STD.ALL;

entity CPU is
    port(
        clock:	     in  STD_LOGIC;
        inM:         in  STD_LOGIC_VECTOR(15 downto 0);
        --instruction: in  STD_LOGIC_VECTOR(15 downto 0);
        --reset:       in  STD_LOGIC;
        outM:        out STD_LOGIC_VECTOR(15 downto 0)
        --writeM:      out STD_LOGIC;
        --addressM:    out STD_LOGIC_VECTOR(14 downto 0);
        --pcout:       out STD_LOGIC_VECTOR(14 downto 0)
  );
end entity;
 

architecture arch of CPU is

	component Mux16 is
		port ( 
				a:   in  STD_LOGIC_VECTOR(15 downto 0);
				b:   in  STD_LOGIC_VECTOR(15 downto 0);
				sel: in  STD_LOGIC;
				q:   out STD_LOGIC_VECTOR(15 downto 0)
		);
	end component;
  
	component ALU is
		port ( 
			x,y:   in STD_LOGIC_VECTOR(15 downto 0); 
			zx:    in STD_LOGIC;                     
			nx:    in STD_LOGIC;                     
			zy:    in STD_LOGIC;                     
			ny:    in STD_LOGIC;                     
			f:     in STD_LOGIC;                     
			no:    in STD_LOGIC;                     
			zr:    out STD_LOGIC;                    
			ng:    out STD_LOGIC;                    
			saida: out STD_LOGIC_VECTOR(15 downto 0) 
		); 
	end component; 
	
	component Register16 is
		port(
			input:   in STD_LOGIC_VECTOR(15 downto 0);
			load:    in STD_LOGIC;
			clock:   in STD_LOGIC;
			output: out STD_LOGIC_VECTOR(15 downto 0)
		);
	end component;
	
	--component instrudec is
	--port(
	--	instruction: in std_logic_vector(15 downto 0);
	--	zr,ng: in std_logic;
	--	muxIOsel, muxAMsel, zx, nx, zy, ny, f, no, loadA, loadD, loadM, loadPC: out std_logic
	--	);
	--end component; 
	
	--component pc is
	--	port(
   --     clock     : in  STD_LOGIC;
	--		increment : in  STD_LOGIC;
	--		load      : in  STD_LOGIC;
	--		reset     : in  STD_LOGIC;
   --      input     : in  std_logic_vector(15 downto 0);
   --      output    : out std_logic_vector(14 downto 0) := "000000000000000"
	-- );
	--end component;

  signal s_clock: std_logic := '0';
  signal s_inM: std_logic_vector(15 downto 0) := (others => '0');
  signal s_instruction: std_logic_vector(15 downto 0) := (others => '0');
  signal s_reset: std_logic := '0';
  signal s_pcout: STD_LOGIC_VECTOR(14 downto 0);
  
  signal muxIOsel: std_logic;
  signal muxAMsel: std_logic;
  signal zx: std_logic := '0'; --precisar ter inicial forçado zero?
  signal nx: std_logic := '0';
  signal zy: std_logic := '0';
  signal ny: std_logic := '0';
  signal f: std_logic := '0';
  signal no: std_logic := '0';
  signal loadA: std_logic := '0';
  signal loadD: std_logic := '0';
  signal loadM: std_logic := '0';
  signal loadPC: std_logic := '0';

  signal zr: std_logic := '0';
  signal ng: std_logic := '0';
  signal muxIOout: std_logic_vector(15 downto 0);
  signal muxAMout: std_logic_vector(15 downto 0);
  signal regAouts: std_logic_vector(15 downto 0);
  signal regDout: std_logic_vector(15 downto 0);
  signal regAout: std_logic_vector(15 downto 0);

  signal saidaALU: std_logic_vector(15 downto 0);
  
  begin
  
   u1 : ALU port map (muxIOout,muxIOout,'0','0','0','0','1','0',open, open,saidaALU);	
	u2 : Register16 port map (s_inM, '1', clock, s_inM);
	u3 : Mux16 port map ( inM, saidaALU, s_inM(0), muxIOout);
	
	outM <= saidaALU;
	
  
	 --DECODE: instrudec port map (instruction, zr, ng, muxIOsel, muxAMsel, zx, nx, zy, ny, f, no, loadA, loadD, loadM, loadPC); --criar instrudec
	 
    --muxIO : mux16 port map (muxIOsel, instruction, saidaALU, muxIOout);
	 
	 --registerA : register16 port map (muxIOout,loadA, clock, regAout); --tamanho regAout
	 
    --muxAM: mux16 port map (muxAMsel, regAout, inM, muxAMout);
	 
    --ALUS : alu port map (regDout, muxAMout, zx, nx, zy, ny, f, no, zr, ng, saidaALU);
	 
    --RegisterD: register16 port map (saidaALU, loadD, clock, regDout); --regDout
	 
	 --PCS: pc port map (clock, '1', loadPC, reset, regAout, s_pcout); --inc, fazer jumps no PC baseados no zr ng com um comparador
	 
	 --pcout <= s_pcout;
	 
	 --AddressM <= regAout(14 downto 0);
	 
	 --OutM <= saidaALU;
	 
	 --WriteM <= loadM;
	 
end architecture;