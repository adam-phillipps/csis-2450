require 'byebug'

class MineSweeper
  def initialize
    @difficulty_map = { 'easy': 0.3, 'medium': 0.6, 'hard': 0.9 }  
    game_info = gather_game_info
    create_board(game_info.shift, game_info.pop)
  end

  def create_board(size = 20, number_of_free_spaces)
    size = 1 if size == 0
    @letters = ('a'..size.chr).to_a
    @board = Hash[letters.map { |letter| [letter, Hash.new(true)] }]
    number_of_free_spaces.times { @board[letters.sample][rand(1..size)] = false }
  end

  def difficulty_decipher(value)
    value.kind_of? Integer ? @letters.size**2 - value : @letters.size * @difficulty_map[value]
  end

  def gather_game_info
    puts 'Welcome to mine-sweeper.  I\'m sorry to say ' +
          'but you might never leave \'cause of how awesome this is.' +
          "\n" + 'how big of a square do you want the board to be?  '
    board_width = gets.chomp.to_i
    puts 'ok, pick a level of difficulty.  There\'s five levels or ' +
          'you can choose the amount of bombs on the board.  You can ' +
          'have from 1 bomb to board.size - 1 bombs.  Keep in mind, your ' +
          'board will have the number you first picked squared squares.'
    difficulty = difficulty_decipher(gets.chomp.to_s)
    [board_width, difficulty]
  end
end

MineSweeper.new