-- Elementos de Sistemas
-- by Luciano Soares
-- ShiftRegisterPISO16.vhd

-- shift register tipo PISO de 16 bits:
-- If load == 1 then load in
-- out = in[0], out = in[1], out = in[2], ...
-- os outros bits podem ser preenchidos com 0

Library ieee; 
use ieee.std_logic_1164.all;

entity ShiftRegisterPISO16 is
	port(
		clock:   in STD_LOGIC;
		input:   in STD_LOGIC_VECTOR(15 downto 0);
		shift:   in STD_LOGIC;
		output: out STD_LOGIC
	);
end entity;
  
architecture arch of ShiftRegisterPISO16 is

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
	signal tmp : STD_LOGIC_VECTOR(15 downto 0);
	signal loadNot : STD_LOGIC;
	
begin 

	loadNot <= not shift;

	u1:FlipFlopD  port map ( clock,  input(0), '1','1', internal(0)  );

	tmp(0) <= (internal(0) and shift) or (input(1) and loadNot);
	u2:FlipFlopD  port map ( clock,  tmp(0), '1','1', internal(1)  );

	tmp(1) <= (internal(1) and shift) or (input(2) and loadNot);
	u3:FlipFlopD  port map ( clock,  tmp(1), '1','1', internal(2)  );

	tmp(2) <= (internal(2) and shift) or (input(3) and loadNot);
	u4:FlipFlopD  port map ( clock,  tmp(2), '1','1', internal(3)  );

	tmp(3) <= (internal(3) and shift) or (input(4) and loadNot);
	u5:FlipFlopD  port map ( clock,  tmp(3), '1','1', internal(4)  );

	tmp(4) <= (internal(4) and shift) or (input(5) and loadNot);
	u6:FlipFlopD  port map ( clock,  tmp(4), '1','1', internal(5)  );

	tmp(5) <= (internal(5) and shift) or (input(6) and loadNot);
	u7:FlipFlopD  port map ( clock,  tmp(5), '1','1', internal(6)  );

	tmp(6) <= (internal(6) and shift) or (input(7) and loadNot);
	u8:FlipFlopD  port map ( clock,  tmp(6), '1','1', internal(7)  );

	tmp(7) <= (internal(7) and shift) or (input(8) and loadNot);
	u9:FlipFlopD  port map ( clock,  tmp(7), '1','1', internal(8)  );

	tmp(8) <= (internal(8) and shift) or (input(9) and loadNot);
	u10:FlipFlopD  port map ( clock,  tmp(8), '1','1', internal(9)  );

	tmp(9) <= (internal(9) and shift) or (input(10) and loadNot);
	u11:FlipFlopD  port map ( clock,  tmp(9), '1','1', internal(10)  );

	tmp(10) <= (internal(10) and shift) or (input(11) and loadNot);
	u12:FlipFlopD  port map ( clock,  tmp(10), '1','1', internal(11)  );

	tmp(11) <= (internal(11) and shift) or (input(12) and loadNot);
	u13:FlipFlopD  port map ( clock,  tmp(11), '1','1', internal(12)  );

	tmp(12) <= (internal(12) and shift) or (input(13) and loadNot);
	u14:FlipFlopD  port map ( clock,  tmp(12), '1','1', internal(13)  );

	tmp(13) <= (internal(13) and shift) or (input(14) and loadNot);
	u15:FlipFlopD  port map ( clock,  tmp(13), '1','1', internal(14)  );

	tmp(14) <= (internal(14) and shift) or (input(15) and loadNot);
	u16:FlipFlopD  port map ( clock,  tmp(14), '1','1', output );



end architecture;