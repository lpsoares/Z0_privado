-- Elementos de Sistemas
-- by Luciano Soares
-- ShiftRegisterSISO16.vhd

-- shift register tipo SISO de 16 bits:
-- out[t+15] = in[t], out[t+16] = in[t+1], out[t+17] = in[t+2], ...

Library ieee; 
use ieee.std_logic_1164.all;
  
entity ShiftRegisterSISO16 is
	port(
		clock:   in STD_LOGIC;
		input:   in STD_LOGIC;
		output: out STD_LOGIC
	);
end entity;

architecture arch of ShiftRegisterSISO16 is

	component FlipFlopD is
		port(
			clock:  in std_logic;
			d:      in std_logic;
			clear:  in std_logic;
			preset: in std_logic;
			q:     out std_logic
		);
	end component;
	
	signal internal : STD_LOGIC_VECTOR(15 downto 0);
	
begin 

	u1:FlipFlopD  port map ( clock,  internal(1), '1','1',      output  );
	u2:FlipFlopD  port map ( clock,  internal(2), '1','1', internal(1)  );
	u3:FlipFlopD  port map ( clock,  internal(3), '1','1', internal(2)  );
	u4:FlipFlopD  port map ( clock,  internal(4), '1','1', internal(3)  );
	u5:FlipFlopD  port map ( clock,  internal(5), '1','1', internal(4)  );
	u6:FlipFlopD  port map ( clock,  internal(6), '1','1', internal(5)  );
	u7:FlipFlopD  port map ( clock,  internal(7), '1','1', internal(6)  );
	u8:FlipFlopD  port map ( clock,  internal(8), '1','1', internal(7)  );
	u9:FlipFlopD  port map ( clock,  internal(9), '1','1', internal(8)  );
	u10:FlipFlopD port map ( clock, internal(10), '1','1', internal(9)  );
	u11:FlipFlopD port map ( clock, internal(11), '1','1', internal(10) );
	u12:FlipFlopD port map ( clock, internal(12), '1','1', internal(11) );
	u13:FlipFlopD port map ( clock, internal(13), '1','1', internal(12) );
	u14:FlipFlopD port map ( clock, internal(14), '1','1', internal(13) );
	u15:FlipFlopD port map ( clock, internal(15), '1','1', internal(14) );
	u16:FlipFlopD port map ( clock,        input, '1','1', internal(15) );

end architecture;