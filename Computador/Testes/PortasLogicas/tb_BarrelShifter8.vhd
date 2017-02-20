library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

library vunit_lib;
context vunit_lib.vunit_context;

entity tb_BarrelShifter8 is
  generic (runner_cfg : string);
end entity;

architecture tb of tb_BarrelShifter8 is

	component BarrelShifter8 is
	port ( 
			a:    in  STD_LOGIC_VECTOR(7 downto 0);   -- input vector
			dir:  in  STD_LOGIC;                       -- 0=>left 1=>right
			size: in  STD_LOGIC_VECTOR(2 downto 0);    -- shift amount
			q:    out STD_LOGIC_VECTOR(7 downto 0));  -- output vector (shifted)
	end component;

	signal  inA, outQ : STD_LOGIC_VECTOR(7 downto 0);
	signal  inDir : STD_LOGIC;
	signal  inSize : STD_LOGIC_VECTOR(2 downto 0);

begin

	mapping: BarrelShifter8 port map(inA, inDir, inSize, outQ);

	main : process
	begin
	test_runner_setup(runner, runner_cfg);

	-- Teste: 1
	inA <= "00000010"; inDir <='0'; inSize <= "000";
	wait for 200 ps;
	assert(outQ = "00000100")  report "Falha em teste: 1" severity error;

	test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;