-- Elementos de Sistemas
-- by Luciano Soares
-- ShiftRegisterPIPO16.vhd

-- shift register tipo PIPO de 16 bits:
-- If direction == 0 then out[t+1] = out[t] << 1
--                   else out[t+1] = out[t] >> 1
-- os novos bits podem ser preenchidos com 0

Library ieee; 
use ieee.std_logic_1164.all;

entity ShiftRegisterPIPO16 is
	port(
		clock:     in  STD_LOGIC;
		input:     in  STD_LOGIC_VECTOR(15 downto 0);
		direction: in  STD_LOGIC;
		output:    out STD_LOGIC_VECTOR(15 downto 0)
	);
end entity;
  
architecture arch of ShiftRegisterPIPO16 is

	component FlipFlopD is
		port(
			clock:  in std_logic;
			d:      in std_logic;
			clear:  in std_logic;
			preset: in std_logic;
			q:     out std_logic
		);
	end component;
	
	component Mux2Way is
		port ( 
			a:   in  STD_LOGIC;
			b:   in  STD_LOGIC;
			sel: in  STD_LOGIC;
			q:   out STD_LOGIC
		);
	end component;
	
	signal internal : STD_LOGIC_VECTOR(15 downto 0);
	
begin 

	u1:FlipFlopD  port map ( clock,  input(0), '1','1', internal(0)  );
	u2:FlipFlopD  port map ( clock,  input(1), '1','1', internal(1)  );
	u3:FlipFlopD  port map ( clock,  input(2), '1','1', internal(2)  );
	u4:FlipFlopD  port map ( clock,  input(3), '1','1', internal(3)  );
	u5:FlipFlopD  port map ( clock,  input(4), '1','1', internal(4)  );
	u6:FlipFlopD  port map ( clock,  input(5), '1','1', internal(5)  );
	u7:FlipFlopD  port map ( clock,  input(6), '1','1', internal(6)  );
	u8:FlipFlopD  port map ( clock,  input(7), '1','1', internal(7)  );
	u9:FlipFlopD  port map ( clock,  input(8), '1','1', internal(8)  );
	u10:FlipFlopD port map ( clock,  input(9), '1','1', internal(9)  );
	u11:FlipFlopD port map ( clock, input(10), '1','1', internal(10) );
	u12:FlipFlopD port map ( clock, input(11), '1','1', internal(11) );
	u13:FlipFlopD port map ( clock, input(12), '1','1', internal(12) );
	u14:FlipFlopD port map ( clock, input(13), '1','1', internal(13) );
	u15:FlipFlopD port map ( clock, input(14), '1','1', internal(14) );
	u16:FlipFlopD port map ( clock, input(15), '1','1', internal(15) );
	
	u17:Mux2Way port map (          '0',  internal(1), direction, output(0)  );
	u18:Mux2Way port map (  internal(0),  internal(2), direction, output(1)  );
	u19:Mux2Way port map (  internal(1),  internal(3), direction, output(2)  );
	u20:Mux2Way port map (  internal(2),  internal(4), direction, output(3)  );
	u21:Mux2Way port map (  internal(3),  internal(5), direction, output(4)  );
	u22:Mux2Way port map (  internal(4),  internal(6), direction, output(5)  );
	u23:Mux2Way port map (  internal(5),  internal(7), direction, output(6)  );
	u24:Mux2Way port map (  internal(6),  internal(8), direction, output(7)  );
	u25:Mux2Way port map (  internal(7),  internal(9), direction, output(8)  );
	u26:Mux2Way port map (  internal(8), internal(10), direction, output(9)  );
	u27:Mux2Way port map (  internal(9), internal(11), direction, output(10) );
	u28:Mux2Way port map ( internal(10), internal(12), direction, output(11) );
	u29:Mux2Way port map ( internal(11), internal(13), direction, output(12) );
	u30:Mux2Way port map ( internal(12), internal(14), direction, output(13) );
	u31:Mux2Way port map ( internal(13), internal(15), direction, output(14) );
	u32:Mux2Way port map ( internal(14),          '0', direction, output(15) );

end architecture;