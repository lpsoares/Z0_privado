-- Elementos de Sistemas
-- by Luciano Soares
-- Register64.vhd

Library ieee; 
use ieee.std_logic_1164.all;
  
entity Register64 is
	port(
		clock:   in STD_LOGIC;
		input:   in STD_LOGIC_VECTOR(63 downto 0);
		load:    in STD_LOGIC;
		output: out STD_LOGIC_VECTOR(63 downto 0)
	);
end entity;
  
architecture arch of Register64 is

	component BinaryDigit is
		port(
			clock:   in STD_LOGIC;
			input:   in STD_LOGIC;
			load:    in STD_LOGIC;
			output: out STD_LOGIC
		);
	end component;
	
begin 

	u1:BinaryDigit  port map ( clock, input(0),  load, output(0)  );
	u2:BinaryDigit  port map ( clock, input(1),  load, output(1)  );
	u3:BinaryDigit  port map ( clock, input(2),  load, output(2)  );
	u4:BinaryDigit  port map ( clock, input(3),  load, output(3)  );
	u5:BinaryDigit  port map ( clock, input(4),  load, output(4)  );
	u6:BinaryDigit  port map ( clock, input(5),  load, output(5)  );
	u7:BinaryDigit  port map ( clock, input(6),  load, output(6)  );
	u8:BinaryDigit  port map ( clock, input(7),  load, output(7)  );
	u9:BinaryDigit  port map ( clock, input(8),  load, output(8)  );
	u10:BinaryDigit port map ( clock, input(9),  load, output(9)  );
	u11:BinaryDigit port map ( clock, input(10), load, output(10) );
	u12:BinaryDigit port map ( clock, input(11), load, output(11) );
	u13:BinaryDigit port map ( clock, input(12), load, output(12) );
	u14:BinaryDigit port map ( clock, input(13), load, output(13) );
	u15:BinaryDigit port map ( clock, input(14), load, output(14) );
	u16:BinaryDigit port map ( clock, input(15), load, output(15) );
	u17:BinaryDigit port map ( clock, input(16), load, output(16) );
	u18:BinaryDigit port map ( clock, input(17), load, output(17) );
	u19:BinaryDigit port map ( clock, input(18), load, output(18) );
	u20:BinaryDigit port map ( clock, input(19), load, output(19) );
	u21:BinaryDigit port map ( clock, input(20), load, output(20) );
	u22:BinaryDigit port map ( clock, input(21), load, output(21) );
	u23:BinaryDigit port map ( clock, input(22), load, output(22) );
	u24:BinaryDigit port map ( clock, input(23), load, output(23) );
	u25:BinaryDigit port map ( clock, input(24), load, output(24) );
	u26:BinaryDigit port map ( clock, input(25), load, output(25) );
	u27:BinaryDigit port map ( clock, input(26), load, output(26) );
	u28:BinaryDigit port map ( clock, input(27), load, output(27) );
	u29:BinaryDigit port map ( clock, input(28), load, output(28) );
	u30:BinaryDigit port map ( clock, input(29), load, output(29) );
	u31:BinaryDigit port map ( clock, input(30), load, output(30) );
	u32:BinaryDigit port map ( clock, input(31), load, output(31) );
	u33:BinaryDigit port map ( clock, input(32), load, output(32) );
	u34:BinaryDigit port map ( clock, input(33), load, output(33) );
	u35:BinaryDigit port map ( clock, input(34), load, output(34) );
	u36:BinaryDigit port map ( clock, input(35), load, output(35) );
	u37:BinaryDigit port map ( clock, input(36), load, output(36) );
	u38:BinaryDigit port map ( clock, input(37), load, output(37) );
	u39:BinaryDigit port map ( clock, input(38), load, output(38) );
	u40:BinaryDigit port map ( clock, input(39), load, output(39) );
	u41:BinaryDigit port map ( clock, input(40), load, output(40) );
	u42:BinaryDigit port map ( clock, input(41), load, output(41) );
	u43:BinaryDigit port map ( clock, input(42), load, output(42) );
	u44:BinaryDigit port map ( clock, input(43), load, output(43) );
	u45:BinaryDigit port map ( clock, input(44), load, output(44) );
	u46:BinaryDigit port map ( clock, input(45), load, output(45) );
	u47:BinaryDigit port map ( clock, input(46), load, output(46) );
	u48:BinaryDigit port map ( clock, input(47), load, output(47) );
	u49:BinaryDigit port map ( clock, input(48), load, output(48) );
	u50:BinaryDigit port map ( clock, input(49), load, output(49) );
	u51:BinaryDigit port map ( clock, input(50), load, output(50) );
	u52:BinaryDigit port map ( clock, input(51), load, output(51) );
	u53:BinaryDigit port map ( clock, input(52), load, output(52) );
	u54:BinaryDigit port map ( clock, input(53), load, output(53) );
	u55:BinaryDigit port map ( clock, input(54), load, output(54) );
	u56:BinaryDigit port map ( clock, input(55), load, output(55) );
	u57:BinaryDigit port map ( clock, input(56), load, output(56) );
	u58:BinaryDigit port map ( clock, input(57), load, output(57) );
	u59:BinaryDigit port map ( clock, input(58), load, output(58) );
	u60:BinaryDigit port map ( clock, input(59), load, output(59) );
	u61:BinaryDigit port map ( clock, input(60), load, output(60) );
	u62:BinaryDigit port map ( clock, input(61), load, output(61) );
	u63:BinaryDigit port map ( clock, input(62), load, output(62) );
	u64:BinaryDigit port map ( clock, input(63), load, output(63) );

end architecture;