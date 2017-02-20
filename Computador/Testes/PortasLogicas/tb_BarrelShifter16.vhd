library ieee;
use ieee.std_logic_1164.all;

library vunit_lib;
context vunit_lib.vunit_context;

entity tb_BarrelShifter16 is
  generic (runner_cfg : string);
end entity;

architecture tb of tb_BarrelShifter16 is

	component BarrelShifter16 is
	port ( 
			a:    in  STD_LOGIC_VECTOR(15 downto 0);   -- input vector
			dir:  in  std_logic;                       -- 0=>left 1=>right
			size: in  std_logic_vector(2 downto 0);    -- shift amount
			q:    out STD_LOGIC_VECTOR(15 downto 0));  -- output vector (shifted)
	end component;

	signal  inA, outQ : STD_LOGIC_VECTOR(15 downto 0);
	signal  inDir : STD_LOGIC;
	signal  inSize : STD_LOGIC_VECTOR(2 downto 0);

begin

	mapping: BarrelShifter16 port map(inA, inDir, inSize, outQ);

	main : process
	begin
	test_runner_setup(runner, runner_cfg);

	-- Teste: 1
	inA <= "0000000000000010"; inDir <='0'; inSize <= "000";
	wait for 200 ps;
	assert(outQ = "0000000000000100")  report "Falha em teste: 1" severity error;

	test_runner_cleanup(runner); -- Simulacao acaba aqui

	end process;
end architecture;